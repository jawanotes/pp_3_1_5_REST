function fillUsersTable() {
    const tableId = document.getElementById("allUsersTable");
    let tableData = "";
    fetch("/api").then(res => res.json())
        .then(users => {
            users.map(user => {
                let roles = [];
                for (let role of user.roles) {
                    roles.push(" " + role.name);
                }
                tableData += `<tr>
        <td>${user.id}</td>
        <td>${user.username}</td>
        <td>${user.password}</td>
        <td>${roles}</td>
        <td><button class="btn btn-info" data-bs-toggle="modal" data-bs-target="#modalEditForm"
        onclick="editUser1(${user.id})">Edit</button></td>
        <td><button class="btn btn-danger" data-bs-toggle="modal" data-bs-target="#modal-delete"
        onclick="deleteUser(${user.id})">Delete</button></td>
        </tr>`;
            })
            tableId.innerHTML = tableData;
        }).catch(error => console.log(error));
}

fillUsersTable();