### built a Node.js/Express server that exposes an /ask endpoint.
https://newsletter.frontendfresh.com/archive/tutorial-interacting-with-openai-in-nodejs-and/
# create an application
mkdir chatgpt_app
#  create a package.json file.
npm init -y

# install the three dependencies we'll need for now.
npm install dotenv express openai

# start server
node index.js

# post a json
curl -X POST http://localhost:5000/ask -H 'content-Type: application/json' -d '{"prompt":"What is the typical weather in Dubai?"}'



### building the UI of our app with the React JavaScript library
https://blog.bitsrc.io/tutorial-build-a-chatbot-with-react-and-openai-2c183c50991e

# started in scaffolding our React app
npm create vite@latest
✔ Project name: custom_chat_gpt_frontend
✔ Select a framework: › React
✔ Select a variant: › JavaScript

#install the project dependencies.
npm install

# run our front-end server ,be presented with the running scaffolded application at http://localhost:5173/.
npm run dev

