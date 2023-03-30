fillAllRoles();
let allroles;

async function fetchRoles() {
    const response = await fetch('/api/roles');
    return await response.json();
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
        console.log(allroles);
        data.forEach(role => {
            let option = document.createElement("option");
            option.value = role.id;
            option.text = role.name;
            //option.id = role.name;
            console.log(option);
            roleSelector1.appendChild(option);
            roleSelector2.appendChild(option);
            roleSelector3.appendChild(option);
        })
    })();

/*    fetch("/api/roles").then(res => res.json())
        .then(roles => {
            allroles = roles;
            console.log(allroles);
            //return roles;
        })
/!*        .then(roles => {

            for(let role of roles) {
                let option = document.createElement("option");
                option.value = role.id;
                option.id = role.name;
                option.text = role.name;

                roleSelector1.appendChild(option);
                roleSelector2.appendChild(option);
                roleSelector3.appendChild(option);
            }
        })*!/
        .catch(error => console.log(error));*/

    //const roleSelector = document.getElementById('rolesEditSelect');

}

