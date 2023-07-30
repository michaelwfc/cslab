import { useEffect, useState } from "react";
import lens from "./assets/lens.png";
import loadingGif from "./assets/loading.gif";
import "./App.css";

function App() {
    // capture the user input value in a state property labeled prompt and we'll initialize it with undefined.
    const [prompt, updatePrompt] = useState(undefined);
    // the loading state of our request
    const [loading, setLoading] = useState(false);
    const [answer, setAnswer] = useState(undefined);

    // React useEffect() Hook to have the {answer} state value set back to undefined if the user ever clears the input
    useEffect(() =>{
        if (prompt!=null && prompt.trim() ===""){
            setAnswer(undefined)
        }
    },[prompt]);

    // use the onKeyDown() event handler and have it trigger a sendPrompt() function we'll create.
    const sendPrompt = async (event) => {
        if (event.key !== "Enter") {
          return;
        }
        try{
            // console.log('prompt', prompt)
            setLoading(true)
            const requestOptions ={
                method:"POST",
                headers : { "Content-Type": "application/json" },
                body:JSON.stringify({prompt})
            }

            const res =  await fetch("api/ask",requestOptions)

            if(!res.ok){
                throw new Erroe("Something went wrong");
            }

            const {message} = await res.json();
            setAnswer(message)

        }catch(err){
            console.error(err,"err")
        }finally{
            setLoading(false)
        }
        
      }

    return (
      <div className="app">
        <div className="app-container">
          <div className="spotlight__wrapper">
          <input
            type="text"
            className="spotlight__input"
            placeholder="Ask me anything..."
            disabled={loading}
            style={{
              backgroundImage: loading ?`url(${loadingGif})` : `url(${lens})`,
            }}
            // update the state prompt value by using the onChange() event handler.
            onChange={(e) => updatePrompt(e.target.value)}
            //
            onKeyDown={(e) => sendPrompt(e)}
          />

          <div className="spotlight__answer">
            {answer && <p>{answer}</p>}
          </div>
          </div>
        </div>
      </div>
    );
  }

export default App;