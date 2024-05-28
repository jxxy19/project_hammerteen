// 토스트 관련 (body 끝쪽 어딘가에 <div id="divForToast"></div> 넣어줘야함.
function showToast(message) {
    $('#liveToast .toast-body').text(message);
    $('#liveToast').toast('show');
}

window.onload = () => {
    document.querySelector('#divForToast').innerHTML += `
        <div class="toast-container position-fixed bottom-0 end-0 p-3" style="z-index: 50000;">
          <div id="liveToast" class="toast " role="alert" aria-live="assertive" aria-atomic="true">
            <div class="toast-header bg-primary text-white">
              <i><svg xmlns="http://www.w3.org/2000/svg" width="16" height="16" fill="currentColor" class="bi bi-info-circle-fill" viewBox="0 0 16 16">
                <path d="M8 16A8 8 0 1 0 8 0a8 8 0 0 0 0 16m.93-9.412-1 4.705c-.07.34.029.533.304.533.194 0 .487-.07.686-.246l-.088.416c-.287.346-.92.598-1.465.598-.703 0-1.002-.422-.808-1.319l.738-3.468c.064-.293.006-.399-.287-.47l-.451-.081.082-.381 2.29-.287zM8 5.5a1 1 0 1 1 0-2 1 1 0 0 1 0 2"/>
              </svg></i>
              <strong class="me-auto ms-1">안내</strong>
              <button type="button" class="btn-close" data-bs-dismiss="toast" aria-label="Close"></button>
            </div>
            <div class="toast-body">
            </div>
          </div>
        </div>
    `;
}


// 회원가입 - 로그인 - 마이페이지
function checkForm(element) {
    event.preventDefault();
    // 회원가입
    if(element.id == 'frmJoin') {
        let checkTarget = ['name', 'userId', 'pwd', 'confirm_pwd', 'phoneNumber', 'birthday', 'email', 'zipCode', 'addr1', 'terms1', 'terms2'];
        // 공란 검사
        for (let element of checkTarget) {
            let target = $('input[name=' + element + ']');
            if (element == 'terms1' || element == 'terms2') {
                if (!$('#' + element).is(":checked")) {
                    showToast("필수 이용약관을 선택해주세요");
                    $(target).focus();
                    return false;
                }
            } else if (element == 'phoneNumber') {
                let phones = document.querySelectorAll('input[name=phoneNumber]');
                for (let phoneEl of phones) {
                    if (!nullCheck($(phoneEl))) {
                        showToast($(phoneEl).data('name') + "을/를 입력해주세요");
                        $(phoneEl).focus();
                        return false;
                    }
                }
            } else if (element == 'addr1') {
                if (!nullCheck2($(target))) {
                    console.log($(target));
                    showToast($(target).data('name') + "을/를 입력해주세요");
                    $(target).focus();
                    return false;
                }
            } else {
                if (!nullCheck($(target))) {
                    console.log($(target));
                    showToast($(target).data('name') + "을/를 입력해주세요");
                    $(target).focus();
                    return false;
                }
            }
        }
        // 정규식 검사
        if (!nameRegCheck($('input[name=name]'))) {
            showToast("이름은 한글로 최소 2글자 이상, 20글자 이하로 작성하세요.");
            $('input[name=name]').focus();
            return false;
        }
        if (!idRegCheck($('input[name=userId]'))) {
            showToast("아이디는 영문 소문자와 숫자로 최소 6글자 이상, 20글자 이하로 작성하세요");
            $('input[name=userId]').focus();
            return false;
        }
        if (!passwordRegCheck($('input[name=pwd]'))) {
            showToast("비밀번호는 영문 소/대문자 + 숫자 + 특수문자를 조합하여 8글자 이상, 20글자 이하로 입력해주세요. 가능한 특수문자 : !@#$%^*+=-");
            $('input[name=pwd]').focus();
            return false;
        }
        if (!emailRegCheck($('input[name=email]'))) {
            showToast("올바른 이메일 형식을 사용해주세요. 예시: abc@email.com");
            $('input[name=email]').focus();
            return false;
        }
        // 핸드폰 번호
        let phones = document.querySelectorAll('input[name=phoneNumber]');
        for (let phoneEl of phones) {
            if (!numberRegCheck($(phoneEl))) {
                showToast("올바른 전화번호 형시으로 입력해주세요");
                $(phoneEl).focus();
                return false;
            }
        }
        // 유효한 생년월일 검사
        if (!dateCheck($('input[name=birthday]'))) {
            showToast("생년월일은 오늘보다 미래일 수 없습니다.");
            $('input[name=birthday]').focus();
            return false;
        }
        // 중복 체크 및 일치 여부 검사
        if ($('input[name=authId]').val() != 'true') {
            showToast("아이디 중복을 확인해주세요");
            $('input[name=userId]').focus();
            return false;
        }
        if ($('input[name=authEmail]').val() != 'true') {
            showToast("이메일 중복을 확인해주세요");
            $('input[name=email]').focus();
            return false;
        }
        if (!passwordMatch($('input[name=pwd]'), $('input[name=confirm_pwd]'))) {
            showToast("비밀번호가 일치하지 않습니다.");
            $('input[name=pwdCheck]').focus();
            return false;
        }
        if (!confirm("가입하시겠습니까?")) {
            return false;
        }

        // 로그인
    } else if (element.id == 'frmLogin') {
        console.log("?????");
        let checkTarget = ['userId', 'pwd'];
        // 공란 검사
        for (let element of checkTarget) {
            let target = $('input[name=' + element + ']');
            if (!nullCheck($(target))) {
                console.log($(target));
                showToast($(target).data('name') + "을/를 입력해주세요");
                $(target).focus();
                return false;
            }
        }

        // 마이페이지
    } else if(element.id == 'frmMypage') {
        let checkTarget = ['name', 'userId', 'pwd', 'confirm_pwd', 'phoneNumber', 'birthday', 'email', 'zipCode', 'addr1'];
        // 공란 검사
        for (let element of checkTarget) {
            let target = $('input[name=' + element + ']');
            if (element == 'phoneNumber') {
                let phones = document.querySelectorAll('input[name=phoneNumber]');
                for (let phoneEl of phones) {
                    if (!nullCheck($(phoneEl))) {
                        showToast($(phoneEl).data('name') + "을/를 입력해주세요");
                        $(phoneEl).focus();
                        return false;
                    }
                }
            } else if (element == 'addr1') {
                if (!nullCheck2($(target))) {
                    console.log($(target));
                    showToast($(target).data('name') + "을/를 입력해주세요");
                    $(target).focus();
                    return false;
                }
            } else if (element == 'pwd' || element == 'confirm_pwd' ) {
                if($(target).val().length > 0 || $('#confirm_pwd').val().length > 0) {
                    if (!nullCheck($(target))) {
                        console.log($(target));
                        showToast($(target).data('name') + "을/를 입력해주세요");
                        $(target).focus();
                        return false;
                    }
                }
            } else {
                if (!nullCheck($(target))) {
                    console.log($(target));
                    showToast($(target).data('name') + "을/를 입력해주세요");
                    $(target).focus();
                    return false;
                }
            }
        }
        // 정규식 검사
        if (!nameRegCheck($('input[name=name]'))) {
            showToast("이름은 한글로 최소 2글자 이상, 20글자 이하로 작성하세요.");
            $('input[name=name]').focus();
            return false;
        }
        if (!idRegCheck($('input[name=userId]'))) {
            showToast("아이디는 영문 소문자와 숫자로 최소 6글자 이상, 20글자 이하로 작성하세요");
            $('input[name=userId]').focus();
            return false;
        }
        if($('#pwd').val().length > 0 || $('#confirm_pwd').val().length > 0) {
            if (!passwordRegCheck($('input[name=pwd]'))) {
                showToast("비밀번호는 영문 소/대문자 + 숫자 + 특수문자를 조합하여 8글자 이상, 20글자 이하로 입력해주세요. 가능한 특수문자 : !@#$%^*+=-");
                $('input[name=pwd]').focus();
                return false;
            }
        }
        if (!emailRegCheck($('input[name=email]'))) {
            showToast("올바른 이메일 형식을 사용해주세요. 예시: abc@email.com");
            $('input[name=email]').focus();
            return false;
        }
        // 핸드폰 번호
        let phones = document.querySelectorAll('input[name=phoneNumber]');
        for (let phoneEl of phones) {
            if (!numberRegCheck($(phoneEl))) {
                showToast("올바른 전화번호 형시으로 입력해주세요");
                $(phoneEl).focus();
                return false;
            }
        }
        // 유효한 생년월일 검사
        if (!dateCheck($('input[name=birthday]'))) {
            showToast("생년월일은 오늘보다 미래일 수 없습니다.");
            $('input[name=birthday]').focus();
            return false;
        }
        // 중복 체크 및 일치 여부 검사
        if ($('input[name=authEmail]').val() != 'true') {
            showToast("이메일 중복을 확인해주세요");
            $('input[name=email]').focus();
            return false;
        }
        if($('#pwd').val().length > 0 || $('#confirm_pwd').val().length > 0) {
            if (!passwordMatch($('input[name=pwd]'), $('input[name=confirm_pwd]'))) {
                showToast("비밀번호가 일치하지 않습니다.");
                $('input[name=pwdCheck]').focus();
                return false;
            }
        }
        if (!confirm("수정하시겠습니까?")) {
            return false;
        }
    }
    element.submit();

}