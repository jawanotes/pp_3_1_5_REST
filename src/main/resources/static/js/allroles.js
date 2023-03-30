fillAllRoles();
let allroles;

async function fetchRoles() {
    const response = await fetch('/api/roles');
    return await response.json();
}

function createOption(role) {
    let option1 = document.createElement("option");
    option1.value = role.id;
    option1.text = role.name;
    return option1;
}
function fillAllRoles() {
    /*const tableId = document.getElementById("allUsersTable");
    let tableData = "";*/

    const roleSelector1 = document.getElementById('rolesEditSelect');
    const roleSelector2 = document.getElementById('rolesNew');
    const roleSelector3 = document.getElementById('deleteRoles');

    (async () => {
        const data = await fetchRoles();
        allroles = data;
        //console.log(allroles);
        data.forEach(role => {
            roleSelector1.appendChild(createOption(role));
            roleSelector2.appendChild(createOption(role));
            roleSelector3.appendChild(createOption(role));
        })
    })();

}

