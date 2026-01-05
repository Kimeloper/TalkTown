//생성 기능
const createButton = document.getElementById('create_btn');

if (createButton) {
    createButton.addEventListener('click', event => {
        if(!confirm("글을 등록하시겠습니까?")){
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

        fetch('/api/boards', {
            method: 'POST',
            headers: {
                'Content-Type': 'application/json',
            },
            body: JSON.stringify({
                title: document.getElementById('title').value,
                content: document.getElementById('content').value
            })
        })
        .then(() => {
           alert('글이 등록되었습니다.');
           location.replace('/boards');
        })
        .catch(error => {
            console.error("등록 중 오류 발생:", error);
            alert("등록을 실패했습니다.");
        });
    });
}



//수정 기능
const updateButton = document.getElementById('update_btn');

if (updateButton){
    updateButton.addEventListener('click', event => {
        if(!confirm('글을 수정하시겠습니까?')){
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

        let params = new URLSearchParams(location.search);
        let id = params.get('id');

        fetch(`/api/boards/${id}`, {
            method: 'PUT',
            headers: {
                "Content-Type": "application/json",
            },
            body: JSON.stringify({
                title: document.getElementById('title').value,
                content: document.getElementById('content').value
            })
         })
        .then(() => {
            alert('글이 수정되었습니다.');
            location.replace(`/boards/${id}`);
        })
        .catch(error => {
            console.error("수정 중 오류 발생:", error);
            alert("수정을 실패했습니다.");
        });
    });
}

//삭제 기능

const deleteButton = document.getElementById('delete_btn');

if(deleteButton){
    deleteButton.addEventListener('click', event => {
        if(!confirm("정말 글을 삭제하시겠습니까? \n(글을 삭제하면 복구할 수 없습니다.)")){
            return;
        }

        const id = document.getElementById('board_id').value;

        fetch(`/api/boards/${id}`, {
            method: 'DELETE'
        })
        .then(() => {
            alert('글이 삭제되었습니다.');
            location.replace('/boards');
        })
        .catch(error => {
            console.error("삭제 중 오류 발생:", error);
            alert("삭제에 실패했습니다.");
        });
    });
}
