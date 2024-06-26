import { defineStore } from "pinia";
import { ref } from "vue";
import { AuthControllerImpl } from "@/controllers/users/auth/auth-controller-impl";
import { UserControllerImpl } from "@/controllers/users/user/user-controller-impl";
import type { AuthController } from "@/controllers/users/auth/auth-controller";
import type { UserController } from "@/controllers/users/user/user-controller";
import { LoginApi, RegisterApi } from "@api/users/auth";
import type { WhoamiApi } from "@api/users/user";
import { usePhotoStore } from "./photo";

export const useUserStore = defineStore(
  "user",
  () => {
    const photoStore = usePhotoStore();

    const isLoggedIn = ref(false);
    const username = ref("");
    const email = ref("");
    const description = ref("");
    const jwt = ref("");

    let refreshing = false;
    async function refresh() {
      if (refreshing) return;
      refreshing = true;
      await whoami();
      console.log("Refreshing user");
      refreshing = false;
    }

    const authController: AuthController = new AuthControllerImpl();
    const userController: UserController = new UserControllerImpl();

    // ==================== AUTH ==================== //
    async function login(parameters: { username: string; password: string }) {
      const response: LoginApi.Response = await authController.login({
        username: parameters.username,
        password: parameters.password,
      });
      if (response.statusCode === 200) {
        isLoggedIn.value = true;
        jwt.value = (response as LoginApi.Responses.Success).access_token;
      } else {
        const typed = response as LoginApi.Errors.Type;
        throw new Error(typed.message);
      }
    }

    async function register(parameters: {
      username: string;
      email: string;
      password: string;
    }) {
      const response = await authController.register({
        username: parameters.username,
        email: parameters.email,
        password: parameters.password,
      });
      if (response.statusCode !== 200) {
        const typed = response as RegisterApi.Errors.Type;
        throw new Error(typed.message);
      }
    }

    async function logout() {
      console.log("Logging out");
      localStorage.clear();
    }

    // ==================== USER ==================== //
    async function whoami() {
      try {
        const response: WhoamiApi.Response = await userController.whoami();
        if (response.statusCode === 200) {
          const typed = response as WhoamiApi.Responses.Success;
          username.value = typed.username;
          email.value = typed.email;
        } else {
          await logout();
          console.log("automatic logout");
        }
      } catch (e) {
        await logout();
        console.log("automatic logout", e);
      }
    }

    async function updatePhoto(newPhoto: string) {
      try {
        const response = await userController.updateUserPhoto({
          photo: newPhoto,
        });
        if (response.statusCode === 200) {
          photoStore.reloadUserPhoto(username.value);
        }
      } catch (e) {
        console.log(e);
      }
    }

    return {
      // ================ Data ================ //
      username,
      email,
      description,
      isLoggedIn,
      jwt,
      // ================ Fetching ================ //
      refresh,
      // ================ Requests ================ //
      login,
      register,
      logout,
      updatePhoto,
    };
  },
  { persist: true }
);
