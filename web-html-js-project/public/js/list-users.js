let listUsersContent = document.getElementById('listUsersContent');
let usersContent = `
    <div class="w3-quarter">
      <img src="https://www.w3schools.com/w3images/girl_train.jpg" style="width:100%" onclick="onClick(this)" alt="Canoeing again">
      <img src="https://www.w3schools.com/w3images/girl_train.jpg" style="width:100%" onclick="onClick(this)" alt="Quiet day at the beach. Cold, but beautiful">
      <img src="https://www.w3schools.com/w3images/girl_train.jpg" style="width:100%" onclick="onClick(this)" alt="The Beach. Me. Alone. Beautiful">
   </div>
    
   <div class="w3-quarter">
      <img src="https://www.w3schools.com/w3images/girl_train.jpg" style="width:100%" onclick="onClick(this)" alt="A girl, and a train passing">
      <img src="https://www.w3schools.com/w3images/girl_train.jpg" style="width:100%" onclick="onClick(this)" alt="Waiting for the bus in the desert">
      <img src="https://www.w3schools.com/w3images/girl_train.jpg" style="width:100%" onclick="onClick(this)" alt="Nature again.. At its finest!">
    </div>
    
    <div class="w3-quarter">
      <img src="https://www.w3schools.com/w3images/girl_train.jpg" style="width:100%" onclick="onClick(this)" alt="Waiting for the bus in the desert">
      <img src="https://www.w3schools.com/w3images/girl_train.jpg" style="width:100%" onclick="onClick(this)" alt="What a beautiful scenery this sunset">
      <img src="https://www.w3schools.com/w3images/girl_train.jpg" style="width:100%" onclick="onClick(this)" alt="The Beach. Me. Alone. Beautiful">
    </div>

    <div class="w3-quarter">
      <img src="https://www.w3schools.com/w3images/girl_train.jpg" style="width:100%" onclick="onClick(this)" alt="A boy surrounded by beautiful nature">
      <img src="https://www.w3schools.com/w3images/girl_train.jpg" style="width:100%" onclick="onClick(this)" alt="A girl, and a train passing">
      <img src="https://www.w3schools.com/w3images/girl_train.jpg" style="width:100%" onclick="onClick(this)" alt="Quiet day at the beach. Cold, but beautiful">
    </div>
    <div class="w3-quarter">
      <img src="https://www.w3schools.com/w3images/girl_train.jpg" style="width:100%" onclick="onClick(this)" alt="A boy surrounded by beautiful nature">
      <img src="https://www.w3schools.com/w3images/girl_train.jpg" style="width:100%" onclick="onClick(this)" alt="A girl, and a train passing">
      <img src="https://www.w3schools.com/w3images/girl_train.jpg" style="width:100%" onclick="onClick(this)" alt="Quiet day at the beach. Cold, but beautiful">
    </div>
    <div class="w3-quarter">
      <img src="https://www.w3schools.com/w3images/girl_train.jpg" style="width:100%" onclick="onClick(this)" alt="A boy surrounded by beautiful nature">
      <img src="https://www.w3schools.com/w3images/girl_train.jpg" style="width:100%" onclick="onClick(this)" alt="A girl, and a train passing">
      <img src="https://www.w3schools.com/w3images/girl_train.jpg" style="width:100%" onclick="onClick(this)" alt="Quiet day at the beach. Cold, but beautiful">
    </div>
`;
listUsersContent.innerHTML = usersContent;

class User{
    constructor(name, email, image, age, status, id){
        this.name   = name;
        this.email  = email;
        this.image  = image;
        this.age    = age || '';
        this.status = status; this.id = id;
    }
}
var listUsers = [];

let urlPage = window.location.pathname;

/* estoy en la pagina '/' */

if(urlPage.length < 3 && (urlPage == '/' || urlPage == '')){  // .limit(999)
    db.collection('user').where('name', '!=', null).get().then((querySnapshot) => {

        querySnapshot.forEach((doc) => {
           let small = doc.data();
            if(small.email && small.image){
               let user = new User(small.name, small.email, small.image, small.age, small.status, small.id);
               let userImage = `<img src="`+user.image+`" width="100%" height="250px" alt="`+user.name+` `+user.email+`">`;  
               listUsers.push(userImage); 
               listUsers.push(userImage); 
               // listUsers.push(userImage); 
            }
        });

        showUsersThums();
    });
}



function showUsersThums(){
   let titlePage = document.getElementById('titlePage');
       titlePage.innerHTML = listUsers.length;

    let imagesRes = '';
    let totalRes = '';
    let userImage = '';
    let divWraper = '';
    for(let i = 0; i < listUsers.length; i++){
        if(listUsers.length < 5){ imagesRes += listUsers[i] }
      //  if(i < 4){
      //      userImage = `<img src="`+listUsers[i].image+`" width="100%" height="250px" alt="`+listUsers[i].name+` `+listUsers[i].email+`">`;
      //      imagesRes+= userImage;
      //      
      //  }

      //  if(i % 4 == 0 && i != 0){
      //       userImage = `<img src="`+listUsers[i].image+`" width="100%" height="250px" alt="`+listUsers[i].name+` `+listUsers[i].email+`">`;
      //          imagesRes+= userImage;
      //       divWraper = `<div class="w3-quarter">`+imagesRes+`</div>`;
      //          totalRes += divWraper;
      //          imagesRes = ''; 
      //  } else {
      //       userImage = `<img src="`+listUsers[i].image+`" width="100%" height="250px" alt="`+listUsers[i].name+` `+listUsers[i].email+`">`;
      //       imagesRes+= userImage;
      //  }
     
    }

    if(listUsers.length < 5){
        divWraper = `<div class="w3-quarter">`+imagesRes+`</div>`;
        totalRes += divWraper;
    }

    
    listUsersContent.innerHTML = totalRes
    console.log('listado Usuarios:', listUsers)
}














let usersNavigationLinks = document.getElementById('usersNavigationLinks');
let linksUsers = `
      <a href="#" class="w3-bar-item w3-button w3-hover-black">«</a>
      <a href="#" class="w3-bar-item w3-black w3-button">1</a>
      <a href="#" class="w3-bar-item w3-button w3-hover-black">2</a>
      <a href="#" class="w3-bar-item w3-button w3-hover-black">3</a>
      <a href="#" class="w3-bar-item w3-button w3-hover-black">4</a>
      <a href="#" class="w3-bar-item w3-button w3-hover-black">»</a>
`;

usersNavigationLinks.innerHTML = linksUsers;

/*
const node = document.createElement('div');
node.innerHTML = linksUsers
listUsersContent.appendChild(node);

*/