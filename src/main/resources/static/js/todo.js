// 삭제 기능
const deleteButton = document.getElementById('delete-btn');

document.querySelectorAll('[id^="delete-btn"]').forEach(button => {
    button.addEventListener('click', event => {
        // 각 카드의 고유한 todo-id 값 얻기
        const id = event.target.parentElement.querySelector('[id="todo-id"]').value;
        fetch(`/todo/${id}`, {
            method: 'DELETE'
        })
            .then(() => {
                alert('삭제가 완료되었습니다.');
                location.replace('/todolist');
            });
    });
});


// 수정 기능
const modifyButton = document.getElementById('modify-btn');

if (modifyButton) {
    modifyButton.addEventListener('click', event => {
        let params = new URLSearchParams(location.search);
        let id = params.get('id');

        fetch(`/todo/${id}`, {
            method: 'PUT',
            headers: {
                "Content-Type": "application/json",
            },
            body: JSON.stringify({
                content: document.getElementById('content').value
            })
        })
            .then(() => {
                alert('수정이 완료되었습니다.');
                location.replace(`/home`);
            });
    });
}

// 생성 기능
const createButton = document.getElementById('create-btn');

if (createButton) {
    createButton.addEventListener('click', event => {
        fetch('/todo', {
            method: 'POST',
            headers: {
                "Content-Type": "application/json",
            },
            body: JSON.stringify({
                content: document.getElementById('content').value
            })
        })
            .then(() => {
                alert('등록 완료되었습니다.');
                location.replace('/todolist');
            });
    });
}