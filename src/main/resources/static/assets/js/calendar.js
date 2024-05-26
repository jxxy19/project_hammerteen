// 일정 offcanvas 초기화
function initCalendarOffCanvas() {
    $('#calendarOffcanvasBackdrop #startDate').val("");
    $('#calendarOffcanvasBackdrop #endDate').val("");
    $('#calendarOffcanvasBackdrop #title').val("");
    $('#calendarOffcanvasBackdrop #description').text("");
    $('#btnDelete').addClass('d-none');
}

// 일정 offcanvas 조회
function setCalendarOffCanvas(type, date) {
    initCalendarOffCanvas();
    if(type === 'new') {
        $('#calendarOffcanvasBackdrop #startDate').val(date + " 09:00");
        $('#calendarOffcanvasBackdrop #endDate').val(date + " 10:00");
    } else {
        $('#btnDelete').removeClass('d-none');
    }
}

// 일정등록
function registPlan() {
    if(confirm("일정을 등록하시겠습니까?")) {
        let startDate =$('#calendarOffcanvasBackdrop #startDate').val();
        let endDate =$('#calendarOffcanvasBackdrop #endDate').val();
        let title = $('#calendarOffcanvasBackdrop #title').val();
        let description = $('#calendarOffcanvasBackdrop #description').val();
        $.ajax({
            url: "/mystudy/registPlan",
            method: 'post',
            dataType : 'json',
            data : {
                "startDateTimeToString" : startDate,
                "endDateTimeToString" : endDate,
                "title" : title,
                "description" : description
            },
            success: function(data) {
                console.log(data);
                alert(data["info"]);
                makeCalendar(startDate);
            },
            error : function(xhr, status, error) {
                console.log("xhr! : " + xhr);
                console.log("status! : " + status);
                console.log("error! : " + error);
            }
        })
    }
}

// 일정삭제


// 일정수정


// 캘린더 그리기
function makeCalendar(date) {
    $('#calendarBody').html("");

    let targetDate = new Date(date);
    let targetMonth = targetDate.getMonth();
    let targetYear = targetDate.getFullYear();
    let targetFullDate = targetYear + "-" + changeNum(targetMonth+1) + "-" + changeNum(targetDate.getDate());
    let firstDate = new Date(targetYear, targetMonth, 1);
    let lastDateObj = new Date(targetYear, (targetMonth+1), 0);
    let lastDate = lastDateObj.getDate();
    let lastWeek = getWeek(lastDateObj);
    let firstDayOfWeek = firstDate.getDay();

    const calendarBody = $('#calendarBody');

    $('#targetYear').text(targetYear);
    $('#targetMonth').text(targetMonth+1);
    $('#datepicker').val(targetFullDate);
    $('#prevMonth').attr('data-prevMonth', getPrevMonthDate(targetDate));
    $('#nextMonth').attr('data-nextMonth', getNextMonthDate(targetDate));

    $.ajax({
        url: "/mystudy/listPlan",
        method: 'post',
        dataType : 'json',
        data : {
            "startDate" : targetFullDate,
        },
        success: function(data) {
            if(data["result"] == "1" ) {
                let initDate = 1;
                for(let i=0; i < lastWeek; i++) {
                    let trEl = document.createElement('tr');
                    for(let j=0; j<7;j++) {
                        let thisDate = targetYear +"-"+changeNum(targetMonth+1)+"-"+changeNum(initDate);
                        let tdEl = document.createElement('td');
                        $(tdEl).addClass('p-1 align-top');
                        if((i<1 && j < firstDayOfWeek) || (initDate > lastDate)) {
                            $(tdEl).html(`
                                <div class="d-flex flex-column justify-content-start"  style="min-height: 130px">
                                    <div class="d-flex justify-content-start div-date"></div>
                                    <div class="div-content"></div>
                                </div>
                                `);
                        } else {
                            $(tdEl).attr('id',thisDate);
                            $(tdEl).html(`
                                <div class="d-flex flex-column justify-content-start"  style="min-height: 130px">
                                    <div class="d-flex justify-content-start div-date">
                                        <button class="btn btn-link p-1 pb-2 ${j === 0 ? 'text-danger' : j === 6 ? 'text-primary' : 'text-dark'}" data-bs-toggle="offcanvas" data-bs-target="#calendarOffcanvasBackdrop" aria-controls="calendarOffcanvasBackdrop" data-date="${thisDate}" onclick="setCalendarOffCanvas('new',this.dataset.date)">${initDate}</button>
                                    </div>
                                    <div class="div-content d-flex flex-column gap-1 justify-content-center">
                                        ${makePlanEl(data["list"],thisDate)}
                                    </div>
                                </div>
                            `);
                            initDate++;
                        }
                        $(trEl).append($(tdEl));
                    }
                    $(calendarBody).append($(trEl));
                }
            } else {
                alert(data["info"]);
            }
        },
        error : function(xhr, status, error) {
            console.log("xhr! : " + xhr);
            console.log("status! : " + status);
            console.log("error! : " + error);
        }
    })
}


function makePlanEl(list, thisDate) {
    let planEl = "";
    for(let i=0;i<list.length;i++){
        let thisDateObj = new Date(thisDate);
        let startDateObj = new Date(list[i]["startDateToString"]);
        let endDateObj = new Date(list[i]["endDateToString"]);
        let startDate = list[i]["startDateTimeToString"];
        let endDate = list[i]["endDateTimeToString"];
        let title = list[i]["title"];
        let idx = list[i]["scheduleIdx"];
        if(startDateObj <= thisDateObj && endDateObj >= thisDateObj) {
            planEl += `<button class="btn bg-label-primary w-100 py-1 px-2 justify-content-start border-primary myText" data-bs-toggle="offcanvas" data-bs-target="#calendarOffcanvasBackdrop" aria-controls="calendarOffcanvasBackdrop" data-startdate="${startDate}" data-startdate="${endDate}" data-idx="${idx}" onclick="setCalendarOffCanvas('modify',this.dataset.startdate, this.dataset.enddate)">${title}</button>`
        }
    }
    return planEl;
}

// 날짜 데이터 셋팅하는 함수들
// 해당 주차 가져오기
function getWeek(date) {
    const currentDate = date.getDate();
    const firstDay = new Date(date.setDate(1)).getDay();

    return Math.ceil((currentDate + firstDay) / 7);
}

// 이전달 시작일 날짜 갖고오기
function getPrevMonthDate(date) {
    let targetDate = new Date(date);
    let targetMonth = targetDate.getMonth();
    let targetYear = targetDate.getFullYear();
    let prevDate = new Date((targetMonth === 0 ? targetYear -1 : targetYear), (targetMonth === 0 ? 11 : targetMonth-1), 1);
    let prevMonth = prevDate.getMonth();
    let prevYear = prevDate.getFullYear();
    let prevFullDate = prevYear + "-" + changeNum(prevMonth+1) + "-" + changeNum(prevDate.getDate());
    return prevFullDate;
}

// 다음달 시작일 날짜 갖고오기
function getNextMonthDate(date) {
    let targetDate = new Date(date);
    let targetMonth = targetDate.getMonth();
    let targetYear = targetDate.getFullYear();
    let nextDate = new Date((targetMonth === 11 ? targetYear +1 : targetYear), (targetMonth === 11 ? 0 : targetMonth+1), 1);
    let nextMonth = nextDate.getMonth();
    let nextYear = nextDate.getFullYear();
    let nextFullDate = nextYear + "-" + changeNum(nextMonth+1) + "-" + changeNum(nextDate.getDate());
    return nextFullDate;
}

// 오늘날짜
function getToday() {
    let today = new Date();
    let todayFullDate = today.getFullYear() + "-" + changeNum(today.getMonth() + 1) + "-" + changeNum(today.getDate());
    return todayFullDate;
}

// 월/일 10미만일 때 앞에 0넣어주기
function changeNum(target) {
    return target < 10 ? "0" + target : target;
}