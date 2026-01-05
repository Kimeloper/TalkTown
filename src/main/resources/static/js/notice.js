//생성 기능
const createButton = $('#create_btn');

if(createButton.length) {
    createButton.on('click', (event) => {
        if(!confirm("공지사항을 등록하시겠습니까?")){
            return;
        }

        const title = document.getElementById('title').value.trim();
        const content = document.getElementById('content').value.trim();

        if(!title){
            alert("제목을 입력해주세요");
            return;
        }
        if(!content){
            alert("내용을 입력해주세요");
            return;
        }

        $.ajax({
            url: `/api/notices`,
            type: 'POST',
            contentType: 'application/json',
            data: JSON.stringify({
                title: $('#title').val(),
                content: $('#content').val()
            }),
            success: function(){
                alert('공지사항이 등록되었습니다.');
                location.replace('/notices');
            },
            error: function(){
                alert('공지사항 등록을 실패했습니다.');
                return;
            }
        });
    });
}

//수정 기능
const modifyButton = $('#update_btn');

if(modifyButton.length){
    modifyButton.on('click', (event) => {
        if(!confirm("공지사항을 수정하시겠습니까?")){
            return;
        }

        const title = document.getElementById('title').value.trim();
        const content = document.getElementById('content').value.trim();

        if(!title){
            alert("제목을 입력해주세요");
            return;
        }
        if(!content){
            alert("내용을 입력해주세요");
            return;
        }

        const params = new URLSearchParams(location.search);
        const id = params.get('id');

        $.ajax({
            url: `/api/notices/${id}`,
            type: 'PUT',
            contentType: 'application/json',
            data: JSON.stringify({
                title: $('#title').val(),
                content: $('#content').val()
            }),
            success: function(){
                alert('공지사항이 수정되었습니다.');
                location.replace(`/notices/${id}`);
            },
            error: function(){
                alert('공지사항 수정을 실패했습니다.');
                return;
            }
        });
    });
}

//삭제 기능
const deleteButton = $('#delete_btn');

if(deleteButton.length){
    deleteButton.on('click', (event) => {
        if(!confirm("정말 공지사항을 삭제하시겠습니까? \n(글을 삭제하면 복구할 수 없습니다.)")){
            return;
        }

        const id = $('#notice_id').val();

        $.ajax({
            url: `/api/notices/${id}`,
            type: 'DELETE',
            success: function(){
            alert('공지사항이 삭제되었습니다.');
            location.replace('/notices');
            },
            error: function(){
                alert('공지사항 삭제를 실패했습니다.');
                return;
            }
        });
    });
}