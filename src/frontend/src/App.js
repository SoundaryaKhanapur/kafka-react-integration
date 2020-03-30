import React, { useState, useEffect } from 'react';
import { Button } from "@material-ui/core";


function App() {

  const [message, setMessage] = useState("");
 
  useEffect(() => {
   fetch('/kafka/getMsgs')
  .then(response => response.json())
  .then(message => {
    setMessage(message);
  });
}, [])

function handleButton(message){
  alert('Retrying');
  console.log(message);
  fetch('kafka/sendMessage/' +message)
  .then( response => response.text())
  .then(message => {
    console.log(message)
  });
}
  return (
    <div>
      <h1>Consumed Messages are: </h1>
      <ol>
        {Object.keys(message).map(msg => {
          return(
            <li>
              <h4>{message[msg]}
              <Button onClick = {() => handleButton(message[msg])}
              color = "primary"
                      variant = "contained"
                      style={{position: "absolute", right: 100}}
                      size = "small"
                      
              >RETRY</Button> 
              </h4>
            </li>
          )
        })}
      </ol>
    </div>
  );
}

export default App;
