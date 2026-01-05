//댓글 등록 기능
const createCommentButton = document.getElementById('comment_create_btn');

if (createCommentButton) {
    createCommentButton.addEventListener('click', event => {
        if(!confirm("댓글을 등록하시겠습니까?")){
            return;
        }

        const boardId = document.getElementById('board_id')?.value;
        const content = document.getElementById('content')?.value;

        if(!content){
            alert("내용을 입력해주세요");
            return;
        }

        fetch('/api/comments', {
            method: 'POST',
            headers: { "Content-Type": "application/json" },
            body: JSON.stringify({ boardId, content })
        })
        .then(() => {
            alert("댓글이 등록되었습니다.");
            location.replace(`/boards/${boardId}`);
        })
        .catch(error => {
            console.error("등록 중 오류 발생:", error);
            alert("등록을 실패했습니다.");
        });
    });
}


//댓글 수정
const modifyCommentButtons = document.querySelectorAll('.comment_update_btn');

modifyCommentButtons.forEach(modifyCommentButton => {
    modifyCommentButton.addEventListener('click', event => {

        const commentDiv = modifyCommentButton.closest('.comment');
        const contentDiv = commentDiv.querySelector('.comment_content');
        const textarea = commentDiv.querySelector('.comment_edit');
        const saveButton = commentDiv.querySelector('.comment_update_save_btn');

        textarea.value = contentDiv.textContent.trim();
        contentDiv.style.display = 'none';
        textarea.style.display = 'block';
        saveButton.style.display = 'inline-block';
        modifyCommentButton.style.display = 'none';

        saveButton.addEventListener('click', event => {
            const newContent = textarea.value.trim();
            const boardId = document.getElementById('board_id')?.value;
            const commentId = commentDiv.dataset.commentId;

            if(!newContent){
                alert("내용을 입력해주세요.");
                return;
            }

            if(!confirm("댓글을 수정 하시겠습니까?")){
                return;
            }

            fetch(`/api/comments/${commentId}`, {
                method: 'PUT',
                headers: { "Content-Type": "application/json" },
                body: JSON.stringify({ content: newContent })
            })
            .then(() => {
                alert('댓글 수정을 완료했습니다.');
                location.replace(`/boards/${boardId}`);
            })
            .catch(error => {
                 console.error("수정 중 오류 발생:", error);
                 alert("수정을 실패했습니다.");
            });
        });
    });
});

//댓글 삭제
const commentDeleteButton = document.querySelectorAll(".comment_delete_btn");

commentDeleteButton.forEach(button => {
    button.addEventListener("click", function () {
        if (!confirm("정말 삭제하시겠습니까?\n(삭제하면 복구할 수 없습니다.)")) {
            return;
        }

        const commentDiv = this.closest(".comment");
        const commentId = commentDiv.getAttribute("data-comment-id");

        fetch(`/api/comments/${commentId}`, {
            method: "DELETE",
            headers: {
                "Content-Type": "application/json"
            }
        })
        .then(() => {
            alert("댓글이 삭제되었습니다.");
            commentDiv.remove();
        })
        .catch(error => {
            console.error("삭제 중 오류 발생:", error);
            alert("삭제에 실패했습니다.");
        });
    });
});