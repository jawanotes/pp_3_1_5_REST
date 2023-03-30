//import {allroles, getAllRoles} from './allroles.js';

/*let roleList = [
    {id: 1, name: "ROLE_USER"},
    {id: 2, name: "ROLE_ADMIN"}
]*/

const idUser = document.getElementById('userIdEdit');
const nameUser = document.getElementById('usernameEdit');
const passwordUser = document.getElementById('userPasswordEdit');
const roleSelector = document.getElementById('rolesEditSelect');

function editUser1(id) {
    fetch(`/api/${id}`).then(res => res.json())
        .then(user => {
            idUser.value = `${user.id}`;
            nameUser.value = `${user.username}`;
            passwordUser.value = ``;
        }).catch(err => console.log(err))

    // fetch(/api/roles).then(res => res.json())
    //     .then(role => {
    //
    //     })
}


const editForm = document.getElementById('editForm');

editForm.onsubmit = async (e) => {
    e.preventDefault();

    let selectedRoles = [];
    let options = document.querySelector('#rolesEditSelect').options;

    for (let i = 0; i < allroles.length; i++) {
        if (options[i].selected) {
            selectedRoles.push(allroles[i])
        }
    }

    let response = await fetch('/api',{
        method: 'PUT',
        body: JSON.stringify({
            id: idUser.value,
            username: nameUser.value,
            password: passwordUser.value,
            roles: selectedRoles
        }),
        headers: {
            'Content-type': 'application/json'
        }}).catch(err => console.log(err));

    if(response.ok) {
        fillUsersTable();
    }

    const modal = document.getElementById('modalEditForm');
    const modalInstance = bootstrap.Modal.getInstance(modal);
    modalInstance.hide();
};
