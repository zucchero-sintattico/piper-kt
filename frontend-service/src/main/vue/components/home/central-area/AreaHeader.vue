<script setup lang="ts">
import {computed, ref} from 'vue'
import {useAppStore} from '@/stores/app'
import ChannelMenu from '@/components/home/left-bar/menu/ChannelMenu.vue'

const appStore = useAppStore()
const channelSettingMenuActive = ref(false)

/**
 * Returns the title of the conversation
 * (either the username of the directs conversation
 * or the name of the channel)
 */
const title = computed(() => {
  if (appStore.selectedDirect !== null) {
    return appStore.selectedDirect
  } else if (appStore.selectedChannel !== null) {
    return appStore.selectedChannel?.name
  } else {
    return ''
  }
})

const isDirect = computed(() => {
  return appStore.selectedDirect != null
})

function handleCallButtonClick() {
  if (appStore.inDirectCall) {
    appStore.setDirectChat()
  } else {
    appStore.setDirectCall()
  }
}
</script>
<template>
  <q-header>
    <q-toolbar>
      <q-toolbar-title>
        {{ title }}
      </q-toolbar-title>
      <q-btn
          v-if="appStore.selectedChannel != null"
          icon="info"
          flat
          round
          @click="channelSettingMenuActive = true"
      />
      <q-btn
          v-if="isDirect && !appStore.inDirectCall"
          icon="video_call"
          flat
          round
          dense
          @click="handleCallButtonClick"
      />
      <q-btn
          v-if="isDirect && appStore.inDirectCall"
          icon="chat"
          flat
          round
          dense
          @click="handleCallButtonClick"
      />
    </q-toolbar>
  </q-header>

  <ChannelMenu v-model:active="channelSettingMenuActive"/>
</template>
