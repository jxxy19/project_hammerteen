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

    let initDate = 1;
    for(let i=0; i < lastWeek; i++) {
        let trEl = document.createElement('tr');
        for(let j=0; j<7;j++) {
            let thisDate = targetYear +"-"+changeNum(targetMonth+1)+"-"+changeNum(initDate);
            let tdEl = document.createElement('td');
            $(tdEl).addClass('p-1 align-top');
            if((i<1 && j < firstDayOfWeek) || (initDate > lastDate)) {
                $(tdEl).html(`
                <div class="d-flex flex-column justify-content-between"  style="min-height: 130px">
                    <div class="d-flex justify-content-start div-date"></div>
                    <div class="div-content"></div>
                </div>
                `);
            } else {
                $(tdEl).attr('id',thisDate);
                $(tdEl).html(`
                <div class="d-flex flex-column justify-content-between"  style="min-height: 130px">
                    <div class="d-flex justify-content-start div-date">
                        <button class="btn btn-link p-1 pb-2 ${j === 0 ? 'text-danger' : j === 6 ? 'text-primary' : 'text-dark'}" data-bs-toggle="offcanvas" data-bs-target="#calendarOffcanvasBackdrop" aria-controls="calendarOffcanvasBackdrop" data-date="${thisDate}">${initDate}</button>
                    </div>
                    <div class="div-content">
                        <button class="btn bg-label-primary w-100 py-1 px-2 justify-content-start border-primary" data-bs-toggle="offcanvas" data-bs-target="#calendarOffcanvasBackdrop" aria-controls="calendarOffcanvasBackdrop" data-date="${thisDate}">내용1</button>
                    </div>
                </div>
                `);
                initDate++;
            }
            $(trEl).append($(tdEl));
        }
        $(calendarBody).append($(trEl));
    }
}

function getWeek(date) {
    const currentDate = date.getDate();
    const firstDay = new Date(date.setDate(1)).getDay();

    return Math.ceil((currentDate + firstDay) / 7);
}

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

function getToday() {
    let today = new Date();
    let todayFullDate = today.getFullYear() + "-" + changeNum(today.getMonth() + 1) + "-" + changeNum(today.getDate());
    return todayFullDate;
}

function changeNum(target) {
    return target < 10 ? "0" + target : target;
}