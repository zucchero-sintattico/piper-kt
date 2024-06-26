import { createApp } from "vue";
import App from "./App.vue";
import router from "./router";
import { createPinia } from "pinia";
import piniaPluginPersistedState from "pinia-plugin-persistedstate";

import { useUserStore } from "./stores/user";

import { Quasar } from "quasar";

// Import icon libraries
import "@quasar/extras/material-icons/material-icons.css";

// Import Quasar css
import "quasar/src/css/index.sass";

const app = createApp(App);
const pinia = createPinia();
app.use(pinia);
pinia.use(piniaPluginPersistedState);

router.beforeEach((to, from, next) => {
  const userStore = useUserStore();
  if (to.meta.requiresAuth && !userStore.isLoggedIn) {
    console.log("Requires auth");
    next("/login");
  } else {
    next();
  }
});

app.use(router);
app.use(Quasar, { plugins: {} });

app.mount("#app");
