<script setup lang="ts">
import { useUserStore } from '@/stores/user'
import { setCssVar } from 'quasar'
import { useAppStore } from '@/stores/app'
import { usePhotoStore } from '@/stores/photo'

const appStore = useAppStore()
const photoStore = usePhotoStore()
const userStore = useUserStore()

const event = defineEmits<{
  (e: 'close'): void
}>()

function handleFileChange(e: any) {
  const file = e.target.files[0]
  if (file) {
    const reader = new FileReader()
    reader.onload = () => {
      userStore.updatePhoto(reader.result as string)
    }
    reader.readAsArrayBuffer(file)
  }
}

function openFileInput() {
  const input = document.getElementById('fileInput')
  input?.click()
}

function updateTheme() {
  setCssVar('primary', appStore.selectedTheme.primary)
  setCssVar('secondary', appStore.selectedTheme.secondary)
  setCssVar('accent', appStore.selectedTheme.accent)
  setCssVar('dark', appStore.selectedTheme.dark)
}

function updateFont() {
  setCssVar('fontfamily', appStore.selectedFont.value)
}
</script>

<template>
  <div class="q-pa-xl bg-white">
    <q-form class="q-gutter-md">
      <!--Insert image with username-->
      <div class="avatar-and-title">
        <q-avatar class="q-mb-md" size="100px" @click="openFileInput" style="cursor: pointer">
          <q-img
            v-if="photoStore.getUserPhoto(userStore.username).value"
            :src="photoStore.getUserPhoto(userStore.username).value"
          >
            <div class="absolute-bottom text-center text-caption">Change Photo</div>
          </q-img>
          <q-img v-else src="../../../assets/user-avatar.png">
            <div class="absolute-bottom text-center text-caption">Change Photo</div>
          </q-img>
        </q-avatar>
        <input
          id="fileInput"
          type="file"
          style="display: none"
          accept="image/*"
          @change="handleFileChange"
        />
        <h3 style="margin-left: 10px">{{ userStore.username }}</h3>
      </div>
      <q-select
        outlined
        v-model="appStore.selectedTheme"
        :options="appStore.ThemesList"
        label="Theme"
        @update:model-value="updateTheme()"
      />

      <q-select
        outlined
        v-model="appStore.selectedFont"
        :options="appStore.FontFamilies"
        label="Font"
        @update:model-value="updateFont()"
      />

      <q-btn
        label="Close"
        type="reset"
        color="primary"
        flat
        class="q-ml-sm q-mt-md"
        @click="event('close')"
      />
    </q-form>
  </div>
</template>
