package org.fullstack4.hammerteen.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.fullstack4.hammerteen.dto.MemberDTO;
import org.fullstack4.hammerteen.dto.ScheduleDTO;
import org.fullstack4.hammerteen.service.ScheduleServiceIf;
import org.fullstack4.hammerteen.util.CommonUtil;
import org.springframework.boot.configurationprocessor.json.JSONArray;
import org.springframework.boot.configurationprocessor.json.JSONException;
import org.springframework.boot.configurationprocessor.json.JSONObject;
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
    @GetMapping("/myReportCardList")
    public void myReportCardListGet(Model model) {
        model.addAttribute("pageType", CommonUtil.setPageType(this.menu1, "성적표"));
    }
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
