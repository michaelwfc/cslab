import { defineConfig } from 'vite'
import react from '@vitejs/plugin-react'

// https://vitejs.dev/config/
// when our front-end makes a request to /api/ask, it gets forwarded to the backend server running at http://localhost:5000/ask.
export default defineConfig({
  plugins: [react()],
  server :{
    proxy:{
      "/api":{
        target:"http://localhost:5000",
        changeOrigin:true,
        rewrite:(path) => path.replace(/^\/api/,""),
      }
    }
  }
});

