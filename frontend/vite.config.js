import vue from '@vitejs/plugin-vue'
import { defineConfig } from 'vite'

export default defineConfig({
  plugins: [vue()],
  server: {
    port: 5173,
    proxy: {
      '/auth': {
        target: 'http://localhost:8080',
        changeOrigin: true
      },
      '/book': {
        target: 'http://localhost:8080',
        changeOrigin: true
      },
      '/borrow': {
        target: 'http://localhost:8080',
        changeOrigin: true
      },
      '/swagger-ui': {
        target: 'http://localhost:8080',
        changeOrigin: true
      },
      '/v3': {
        target: 'http://localhost:8080',
        changeOrigin: true
      }
    }
  }
})
