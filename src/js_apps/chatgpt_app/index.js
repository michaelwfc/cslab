// configure dotenv
require("dotenv").config();

const express = require("express");
const app = express();
//a built-in middleware function in Express. 
// It parses incoming requests with JSON payloads and is based on body-parser.
app.use(express.json());
// var bodyParser = require('body-parser');
// app.use(bodyParser.json()); // for parsing application/json
const port = process.env.PORT || 5000;

// import modules from OpenAI library
const { Configuration, OpenAIApi } = require("openai");

// instantiating the Configuration() constructor
const configuration = new Configuration({
    apiKey: process.env.OPENAI_API_KEY,
  });
//  set up a new instance of the OpenAI API class
const openai = new OpenAIApi(configuration);

console.log(process.env.OPENAI_API_KEY)


app.post("/ask", async (req, res) => {
    const prompt = req.body.prompt;
  
    try {
      if (prompt == null) {
        throw new Error("Uh oh, no prompt was provided");
      }
  
      // trigger OpenAI completion
      // const response = await openai.createCompletion(
      //   {
      //       model: "text-davinci-003",
      //       prompt:prompt,
      //     }
      // );
      
  
      // retrieve the completion text from response
    // const completion = response.data.choices[0].text;
    const response = "try to get response from openai"
    const completion = response
    
    return res.status(200).json({
        success: true,
        message: completion,
      });

    } catch (error) {
      console.log(error.message);
      return res.status(400).json({
        success: false,
        message: "no prompt was provided",
      });
    }
  });


  app.listen(port, () => console.log(`Server is running on port ${port}!!`));
