import { defineStore } from "pinia";
import { computed, ref, type Ref } from "vue";
import type { UserController } from "@/controllers/users/user/user-controller";
import { UserControllerImpl } from "@/controllers/users/user/user-controller-impl";
import type { GetUserPhotoApi } from "@api/users/user";

export const usePhotoStore = defineStore("photo", () => {
  const userController: UserController = new UserControllerImpl();
  const usersPhotos = ref<Record<string, string | undefined>>({});

  async function reloadUserPhoto(targetUsername: string) {}

  function getUserPhoto(targetUsername: string): Ref<string | undefined> {
    return computed(() => usersPhotos.value[targetUsername]);
  }

  return {
    reloadUserPhoto,
    getUserPhoto,
  };
});
