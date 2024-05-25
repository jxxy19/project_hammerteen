var IMP = window.IMP;
IMP.init("imp44246027");

var today = new Date();
var hours = today.getHours(); // 시
var minutes = today.getMinutes();  // 분
var seconds = today.getSeconds();  // 초
var milliseconds = today.getMilliseconds();
var makeMerchantUid = hours +  minutes + seconds + milliseconds;


// 결제창 연결 및 결제 진행
function requestPay() {
    if($('#agreeCheck').is(':checked')) {
        const name = $('#name').text();
        const email = $('#email').text();
        const phone = $('#phone').text();
        const amount = uncomma($('#amount').text());
        const userId = $('#userId').val();
        const lectureIdxes = document.querySelectorAll('input[name=lectureIdx]');
        IMP.request_pay({
            pg : 'html5_inicis',
            pay_method : 'card',
            merchant_uid: "IMP"+makeMerchantUid,
            name : '헤머틴 수강신청',
            amount : amount,
            buyer_email : email,
            buyer_name : name,
            buyer_tel : phone,
        }, function (rsp) { // callback
            if (rsp.success) {
                console.log(rsp);
                payInfoSubmit(rsp,userId,lectureIdxes);
            } else {
                alert("결제에 실패하였습니다.");
            }
        });
    } else {
        alert("주문내용을 확인 후 구매조건 및 개인정보처리방침과 결제에 동의해주세요");
    }
}

// 결제정보 form으로 던지기
function payInfoSubmit(rsp, userId, lectureIdxes){
    let formEl = document.createElement('form');
    let inputEl;

    for(let lectureIdx of lectureIdxes) {
        inputEl = document.createElement('input');
        inputEl.type = 'hidden';
        inputEl.name = 'lectureIdxes';
        inputEl.value = lectureIdx.value;
        formEl.append(inputEl);
    }

    inputEl = document.createElement('input');
    inputEl.type = 'hidden';
    inputEl.name = 'paymentAmount';
    inputEl.value = rsp.paid_amount;
    formEl.append(inputEl);

    inputEl = document.createElement('input');
    inputEl.type = 'hidden';
    inputEl.name = 'userId';
    inputEl.value = userId;
    formEl.append(inputEl);

    inputEl = document.createElement('input');
    inputEl.type = 'hidden';
    inputEl.name = 'userName';
    inputEl.value = rsp.buyer_name;
    formEl.append(inputEl);

    inputEl = document.createElement('input');
    inputEl.type = 'hidden';
    inputEl.name = 'userPhoneNumber';
    inputEl.value = rsp.buyer_tel;
    formEl.append(inputEl);

    inputEl = document.createElement('input');
    inputEl.type = 'hidden';
    inputEl.name = 'userEmail';
    inputEl.value = rsp.buyer_email;
    formEl.append(inputEl);

    inputEl = document.createElement('input');
    inputEl.type = 'hidden';
    inputEl.name = 'paymentMethod';
    inputEl.value = rsp.pay_method;
    formEl.append(inputEl);

    inputEl = document.createElement('input');
    inputEl.type = 'hidden';
    inputEl.name = 'applyNum';
    inputEl.value = rsp.apply_num;
    formEl.append(inputEl);

    inputEl = document.createElement('input');
    inputEl.type = 'hidden';
    inputEl.name = 'paymentCompany';
    inputEl.value = rsp.pg_provider;
    formEl.append(inputEl);

    inputEl = document.createElement('input');
    inputEl.type = 'hidden';
    inputEl.name = 'paymentMerchantId';
    inputEl.value = rsp.merchant_uid;
    formEl.append(inputEl);

    inputEl = document.createElement('input');
    inputEl.type = 'hidden';
    inputEl.name = 'paymentPgId';
    inputEl.value = rsp.pg_tid;
    formEl.append(inputEl);

    inputEl = document.createElement('input');
    inputEl.type = 'hidden';
    inputEl.name = 'receiptUrl';
    inputEl.value = rsp.receipt_url;
    formEl.append(inputEl);

    formEl.method = 'post';
    formEl.action = '/payment/payment';

    document.body.append(formEl);
    formEl.submit();
}

function refundThis(element) {
    if(confirm("환불신청하시겠습니까?")) {
        const paymentIdx = element.dataset.paymentidx;

        let formEl = document.createElement('form');
        let inputEl = document.createElement('input');

        inputEl.type = 'hidden';
        inputEl.name = 'paymentIdx';
        inputEl.value = paymentIdx;
        formEl.append(inputEl);

        formEl.method = 'post';
        formEl.action = '/payment/refund';

        document.body.append(formEl);
        formEl.submit();
    }
}

function confirmThis(element) {
    if(confirm("구매확정 이후에는 환불이 불가합니다.\n구매확정하시겠습니까?")) {
        const paymentIdx = element.dataset.paymentidx;

        let formEl = document.createElement('form');
        let inputEl = document.createElement('input');

        inputEl.type = 'hidden';
        inputEl.name = 'paymentIdx';
        inputEl.value = paymentIdx;
        formEl.append(inputEl);

        formEl.method = 'post';
        formEl.action = '/payment/confirm';

        document.body.append(formEl);
        formEl.submit();
    }
}