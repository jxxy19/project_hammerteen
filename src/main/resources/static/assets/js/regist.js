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