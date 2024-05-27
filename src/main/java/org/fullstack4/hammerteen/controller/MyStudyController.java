package org.fullstack4.hammerteen.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.fullstack4.hammerteen.dto.*;
import org.fullstack4.hammerteen.service.LectureServiceIf;
import org.fullstack4.hammerteen.service.MemberServiceIf;
import org.fullstack4.hammerteen.service.ScheduleServiceIf;
import org.fullstack4.hammerteen.util.CommonUtil;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Log4j2
@Controller
@RequestMapping(value="/mystudy")
@RequiredArgsConstructor
public class MyStudyController {
    private final ScheduleServiceIf scheduleServiceIf;
    private final LectureServiceIf lectureServiceIf;
    private final MemberServiceIf memberServiceIf;
    private final ObjectMapper objectMapper;

    private String menu1 = "나의 학습방";
    @GetMapping("/myLectureList")
    public void myLectureListGet(Model model) {
        model.addAttribute("pageType", CommonUtil.setPageType(this.menu1, "나의 강의실"));
    }
    @GetMapping("/myLectureView")
    public void myLectureViewGet(Model model) {
        model.addAttribute("pageType", CommonUtil.setPageType(this.menu1, "나의 강의실"));
    }
    @GetMapping("/myLecturePlay")
    public void myLecturePlayGet(Model model) {
        model.addAttribute("pageType", CommonUtil.setPageType(this.menu1, "나의 강의실"));
    }

    // 나의 성적표 관련
    @GetMapping("/myReportCardList")
    public String myReportCardListGet(Model model,
                                      HttpSession session,
                                      RedirectAttributes redirectAttributes,
                                      @RequestParam(name="lectureIdx", defaultValue = "0")String lectureIdx) {
        MemberDTO memberDTO = (MemberDTO) session.getAttribute("memberDTO");
        if(memberDTO != null) {
            String memberRole = memberDTO.getRole();
            String userId = memberDTO.getUserId();
            int userIdx = memberDTO.getMemberIdx();
            if(memberRole.equals("user")) {
                List<MyLectureDTO> myLectureDTOList = lectureServiceIf.myLectureList(userId);
                myLectureDTOList.forEach(dto->{
                    dto.setLectureDTO(lectureServiceIf.selectLectureDTOByIdx(CommonUtil.parseInt(lectureIdx)));
                    dto.setMemberDTO(memberServiceIf.view(userId));
                    dto.setLectureScoreDTO(lectureServiceIf.myScore(userId, CommonUtil.parseInt(lectureIdx)));
                    dto.setDateToString();
                });
                model.addAttribute("scoreList", myLectureDTOList);
            } else {
                if(memberRole.equals("teacher")) {
                    List<LectureDTO> lectureDTOList = lectureServiceIf.lectureListForTeacher(userIdx);
                    model.addAttribute("lectureList", lectureDTOList);
                } else if(memberRole.equals("admin")) {
                    List<LectureDTO> lectureDTOList = lectureServiceIf.lectureListForAdmin();
                    model.addAttribute("lectureList", lectureDTOList);
                }
                if(!lectureIdx.equals("0")) {
                    List<MyLectureDTO> myLectureDTOList = lectureServiceIf.studentList(CommonUtil.parseInt(lectureIdx));
                    myLectureDTOList.forEach(dto->{
                        dto.setMemberDTO(memberServiceIf.view(dto.getUserId()));
                        dto.setLectureDTO(lectureServiceIf.selectLectureDTOByIdx(CommonUtil.parseInt(lectureIdx)));
                        dto.setLectureScoreDTO(lectureServiceIf.myScore(dto.getUserId(), CommonUtil.parseInt(lectureIdx)));
                    });
                    model.addAttribute("studentList", myLectureDTOList);
                }
            }
            model.addAttribute("memberRole", memberRole);
            model.addAttribute("pageType", CommonUtil.setPageType(this.menu1, "성적표"));
            return "/mystudy/myReportCardList";
        } else {
            redirectAttributes.addFlashAttribute("info", "로그인 정보 없음");
            return "redirect:/";
        }
    }
    
    @PostMapping("/myReportCardList")
    public String myReportCardListPOST(@RequestParam(name="lectureIdx", defaultValue = "0")String lectureIdx,
                                       @RequestParam(name = "userId", defaultValue = "")String userId,
                                       @RequestParam(name = "score", defaultValue = "")String score,
                                       RedirectAttributes redirectAttributes) {
        String[] userIdArr = userId.split(",");
        String[] scoreArr = score.split(",");
        int result = 0;
        for(int i = 0; i < userIdArr.length; i++) {
            LectureScoreDTO lectureScoreDTO = lectureServiceIf.myScore(userIdArr[i], CommonUtil.parseInt(lectureIdx));
            if(lectureScoreDTO != null) {
                lectureScoreDTO.setLectureScore(CommonUtil.parseInt(scoreArr[i]));
            } else {
                lectureScoreDTO = LectureScoreDTO.builder()
                        .lectureIdx(CommonUtil.parseInt(lectureIdx))
                        .userId(userIdArr[i])
                        .lectureScore(CommonUtil.parseInt(scoreArr[i]))
                        .build();
            }
            int idx = lectureServiceIf.saveLectureScore(lectureScoreDTO);
            if(idx > 0) {result++;}
        }
        if(result == userIdArr.length) {
            redirectAttributes.addFlashAttribute("info", "성적 등록 성공");
            return "redirect:/mystudy/myReportCardList?lectureIdx="+lectureIdx;
        }
        redirectAttributes.addFlashAttribute("info", "성적 등록 실패");
        return "/";
    }


    // 학습계획표 관련
    @GetMapping("/myStudyPlan")
    public void myStudyPlanGet(Model model) {
        model.addAttribute("pageType", CommonUtil.setPageType(this.menu1, "학습계획표"));
    }

    // 지현추가 : AJAX 학습 계획표 관련
    @RequestMapping(value = {"/registPlan", "/modifyPlan"}, method = RequestMethod.POST, produces = "application/json;charset=UTF-8")
    @ResponseBody
    public String registOrModifyPlanPOST(ScheduleDTO scheduleDTO,
                              BindingResult bindingResult,
                              HttpSession session) {
        log.info("scheduleDTO : {}", scheduleDTO);
        Map<String, String> resultMap = new HashMap<>();
        MemberDTO memberDTO = (MemberDTO) session.getAttribute("memberDTO");
        log.info("memberDTO : {}", memberDTO);
        if(memberDTO != null) {
            if(bindingResult.hasErrors()) {
                resultMap.put("result", "0");
                resultMap.put("info", "입력 정보 부족");
            }
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");
            scheduleDTO.setStartDate(LocalDateTime.parse(scheduleDTO.getStartDateTimeToString(),formatter));
            scheduleDTO.setEndDate(LocalDateTime.parse(scheduleDTO.getEndDateTimeToString(),formatter));
            scheduleDTO.setUserId(memberDTO.getUserId());
            int scheduleIdx = scheduleServiceIf.registSchedule(scheduleDTO);
            if(scheduleIdx > 0) {
                resultMap.put("result", "1");
                resultMap.put("info", "저장 성공");
            } else {
                resultMap.put("result", "0");
                resultMap.put("info", "저장 실패");
            }
        } else {
            resultMap.put("result", "0");
            resultMap.put("info", "아이디 정보 없음");
        }
        JSONObject resultJSON = new JSONObject(resultMap);
        return resultJSON.toString().replace("=", ":");
    }


    @RequestMapping(value = "/listPlan", method = RequestMethod.POST, produces = "application/json;charset=UTF-8")
    @ResponseBody
    public String listPlanPOST(HttpSession session,
                               @RequestParam(name = "startDate", defaultValue = "null") String startDate) {
        Map<String, Object> resultMap = new HashMap<>();
        MemberDTO memberDTO = (MemberDTO) session.getAttribute("memberDTO");
        if(memberDTO != null) {
            if(!startDate.equals("null")) {
                String[] dateArr = startDate.split("-");
                String year = dateArr[0];
                String month = dateArr[1];
                List<ScheduleDTO> scheduleDTOList = scheduleServiceIf.list(memberDTO.getUserId(), year, month);
                JSONArray jsonArray = new JSONArray();
                scheduleDTOList.forEach(dto -> {
                    Map map = objectMapper.convertValue(dto, Map.class);
                    JSONObject obj = new JSONObject(map);
                    jsonArray.put(obj);
                });
                resultMap.put("result", "1");
                resultMap.put("info", "조회성공");
                resultMap.put("list",jsonArray);
            } else {
                resultMap.put("result", "0");
                resultMap.put("info", "올바르지 않은 일자 조회");
            }
        } else {
            resultMap.put("result", "0");
            resultMap.put("info", "아이디 정보 없음");
        }
        JSONObject resultJSON = new JSONObject(resultMap);
        return resultJSON.toString().replace("=", ":");
    }

    @RequestMapping(value = "/viewPlan", method = RequestMethod.POST, produces = "application/json;charset=UTF-8")
    @ResponseBody
    public String viewPlanPOST(HttpSession session,
                               @RequestParam(name = "scheduleIdx", defaultValue = "0") String scheduleIdx) {
        Map<String, Object> resultMap = new HashMap<>();
        MemberDTO memberDTO = (MemberDTO) session.getAttribute("memberDTO");
        if(memberDTO != null) {
            if(!scheduleIdx.equals("0")) {
                ScheduleDTO scheduleDTO = scheduleServiceIf.view(CommonUtil.parseInt(scheduleIdx));
                Map map = objectMapper.convertValue(scheduleDTO, Map.class);
                JSONObject obj = new JSONObject(map);
                resultMap.put("result", "1");
                resultMap.put("info", "조회성공");
                resultMap.put("obj",obj);
            } else {
                resultMap.put("result", "0");
                resultMap.put("info", "올바르지 않은 일정 인덱스 조회");
            }
        } else {
            resultMap.put("result", "0");
            resultMap.put("info", "아이디 정보 없음");
        }
        JSONObject resultJSON = new JSONObject(resultMap);
        return resultJSON.toString().replace("=", ":");
    }

    @RequestMapping(value = "/deletePlan", method = RequestMethod.POST, produces = "application/json;charset=UTF-8")
    @ResponseBody
    public String deletePlanPOST(HttpSession session,
                               @RequestParam(name = "scheduleIdx", defaultValue = "0") String scheduleIdx) {
        Map<String, Object> resultMap = new HashMap<>();
        MemberDTO memberDTO = (MemberDTO) session.getAttribute("memberDTO");
        if(memberDTO != null) {
            if(!scheduleIdx.equals("0")) {
                scheduleServiceIf.deleteSchedule(CommonUtil.parseInt(scheduleIdx));
                resultMap.put("result", "1");
                resultMap.put("info", "삭제성공");
            } else {
                resultMap.put("result", "0");
                resultMap.put("info", "올바르지 않은 일정 인덱스 조회");
            }
        } else {
            resultMap.put("result", "0");
            resultMap.put("info", "아이디 정보 없음");
        }
        JSONObject resultJSON = new JSONObject(resultMap);
        return resultJSON.toString().replace("=", ":");
    }

}
