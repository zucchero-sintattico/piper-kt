import { fileURLToPath, URL } from "node:url";

import { quasar, transformAssetUrls } from "@quasar/vite-plugin";
import vue from "@vitejs/plugin-vue";
import vueJsx from "@vitejs/plugin-vue-jsx";
import { defineConfig } from "vite";

// https://vitejs.dev/config/
export default defineConfig({
  plugins: [
    vue({ template: { transformAssetUrls } }),
    quasar({ sassVariables: "src/main/vue/assets/quasar-variables.sass" }),
    vueJsx(),
  ],
  resolve: {
    alias: {
      "@api": fileURLToPath(new URL("./src/main/vue/api", import.meta.url)),
      "@": fileURLToPath(new URL("./src/main/vue", import.meta.url)),
    },
  },
  cacheDir: "./node_modules/.vite",
  server: {
    proxy: {
      "^(?!/site).*$": {
        // target for testing with production backend
        target: "http://localhost",
        // target for single service testing
        // target: 'http://localhost:3000',
        changeOrigin: true,
        ws: true,
      },
    },
  },
});
