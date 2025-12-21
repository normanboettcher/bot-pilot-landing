import {defineConfig} from 'vite'
import react from '@vitejs/plugin-react'
import {resolve} from ".pnp.loader.mjs";

// https://vite.dev/config/
export default defineConfig({
    plugins: [react()],
    resolve: {
        alias: {
            '@': resolve('./src'),
        }
    }
})
