// 썸머노트
$('#summernote').summernote({
    placeholder: 'Hello stand alone ui',
    tabsize: 2,
    height: 500,
    toolbar: [
        ['style', ['style']],
        ['font', ['bold', 'underline', 'clear']],
        ['color', ['color']],
        ['para', ['paragraph']],
        ['table', ['table']],
        ['insert', ['link', 'picture', 'video']],
        ['view', ['fullscreen', 'codeview', 'help']]
    ]
});

// 파일 리스트 조작용(파일 추가)
function fileList(element) {
    document.querySelector('#file-list').innerHTML = "";
    let fileList = document.querySelector('#file-list');
    for (let i=0; i < element.files.length; i++) {
        let list = document.createElement('li');
        list.classList.add('card', 'd-flex', 'flex-row', 'justify-content-between', 'p-2', 'fileListNodes');
        list.dataset.idx = i;
        list.innerHTML = '<span>' + element.files.item(i).name + '</span><span><a id="deleteButton" class="text-danger font-weight-bold pr-2" href="#" data-idx="'+i+'" onclick="deleteThisFile(this)">X</a></span>'
        fileList.append(list);
    }
}
// 파일 리스트 개별 삭제용
function deleteThisFile(element) {
    event.preventDefault();
    element.parentElement.parentElement.remove();
    const dataTransfer = new DataTransfer();
    let target = element.dataset.idx;
    let files = document.querySelector('#file').files;
    let fileArray = Array.from(files);
    fileArray.splice(target, 1);
    fileArray.forEach(file => {dataTransfer.items.add(file);});
    document.querySelector('#file').files = dataTransfer.files;
    let filelies = document.querySelectorAll('.fileListNodes');
    for(let i = 0; i < filelies.length; i++) {
        filelies[i].dataset.idx = i;
        filelies[i].querySelector('#deleteButton').dataset.idx = i;
    }
}
function deleteThisFile2(element,bbsFileIdx) {
    if(confirm("파일을 삭제하시겠습니까?")) {
        element.parentElement.parentElement.remove();
        const dataTransfer = new DataTransfer();
        let target = element.dataset.idx;
        let files = document.querySelector('#file').files;
        let fileArray = Array.from(files);
        fileArray.splice(target, 1);
        fileArray.forEach(file => {
            dataTransfer.items.add(file);
        });
        document.querySelector('#file').files = dataTransfer.files;
        let filelies = document.querySelectorAll('.fileListNodes');
        for (let i = 0; i < filelies.length; i++) {
            filelies[i].dataset.idx = i;
            filelies[i].querySelector('#deleteButton').dataset.idx = i;
        }
        $.ajax({
            url: '/board/deletefile',
            type: 'GET',
            contentType: 'application/x-www-form-urlencoded',
            dataType: 'json',
            data: {bbsFileIdx: bbsFileIdx},

            success: function (response) {
               alert("삭제가 성공하였습니다.");
            },
            error: function () {
                alert("서버와의 통신 중 오류가 발생했습니다.");
            }
        });
    }
}

function addInput() {
    let subList = document.querySelector('#subList');
    let subs = document.querySelectorAll('#subList .sub');
    let subsCnt = subs.length + 1;
    $(subList).append(`
        <div class="sub row align-items-stretch gap-1">
            <div class="col-11 border rounded-start py-4">
                <div class="input-group input-group-merge d-flex flex-column ">
                    <small class="d-block subNum text-primary fw-bold">${subsCnt}회차</small>
                    <label class="form-label" for="subTitle">회차 명 <span class="text-danger fw-bold">*</span></label>
                    <div class="input-group input-group-merge">
                        <input type="text" class="form-control" id="subTitle">
                    </div>
                </div>
                <div class="form-group">
                    <label class="form-label">강의 영상 <span class="text-danger fw-bold">*</span></label>
                    <input class="form-control" name="video${subsCnt}" type="file" id="video${subsCnt}">
                </div>
            </div>
            <div class="col-1 rounded-end btn bg-white btn-outline-primary me-n1">
                <button type="button" class="btn h-100 w-100 text-primary btn-delete" onclick="deleteThis(this)">x</button>
            </div>
        </div>
    `);
}

function deleteThis(element) {
    let subList = document.querySelector('#subList');
    let subs = document.querySelectorAll('#subList .sub');
    let subsCnt = subs.length + 2;
    if(subs.length > 1) {
        element.parentElement.parentElement.remove();
        let subs2 = document.querySelectorAll('#subList .sub');
        for(let i=0; i < subs2.length ; i++) {
            let target = subs2[i].querySelector('input[type=file]');
            let target2 = subs2[i].querySelector('.subNum');
            target.name = 'video'+ (i+1);
            target.id = 'video'+ (i+1);
            target2.innerText = (i+1) + '회차';
        }
    } else {
        alert("최소 1개의 입력칸이 필요합니다.")
    }
}
function deleteThisNoLimit(element) {
    element.parentElement.parentElement.remove();
}

