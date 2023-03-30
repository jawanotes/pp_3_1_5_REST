const deleteId = document.getElementById('deleteId');
const usernameDel = document.getElementById('deleteUsername');
const passwordDel = document.getElementById('deletePassword');

function deleteUser(id) {
    fetch(`/api/${id}`).then(res => res.json())
        .then(user => {
            deleteId.value = `${user.id}`;
            usernameDel.value = `${user.username}`;
            passwordDel.value = `${user.password}`;
        }).catch(err => console.log(err))
}

const formDelete = document.getElementById('delete-form');

formDelete.onsubmit = async (e) => {
    e.preventDefault();

    let response = await fetch(`/api/${deleteId.value}`,{
        method: 'DELETE'}).catch(err => console.log(err));

    if(response.ok) {
        fillUsersTable();
    }

    const modal = document.getElementById('modal-delete');
    const modalInstance = bootstrap.Modal.getInstance(modal);
    modalInstance.hide();
};