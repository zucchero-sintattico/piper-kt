<script setup lang="ts">
import { useServerStore } from '@/stores/server'
import { ref } from 'vue'
import { CreateChannelApi } from '@api/piperchat/channel'
import { useAppStore } from '@/stores/app'

const event = defineEmits<{
  (e: 'result', error?: string): void
  (e: 'close'): void
}>()

const appStore = useAppStore()
const serverStore = useServerStore()

const name = ref('')
const description = ref('')
const channelType = ref(CreateChannelApi.ChannelType.Messages)

async function onSubmit() {
  try {
    await serverStore.createChannel(
      name.value,
      description.value,
      channelType.value,
      appStore.selectedServer!.id
    )
    event('result')
  } catch (e) {
    event('result', String(e))
  }
}
</script>

<template>
  <div class="q-pa-xl bg-white">
    <q-form class="q-gutter-md" @submit="onSubmit">
      <h2 class="text-h3">Create a new channel in {{ appStore.selectedServer?.name }} server</h2>

      <q-input
        filled
        v-model="name"
        label="Channel name"
        hint="Name of your channler"
        lazy-rules
        :rules="[(val) => (val && val.length > 0) || 'Please type something']"
      />

      <q-input
        filled
        v-model="description"
        label="Channel description"
        hint="Description of your channel"
        lazy-rules
      />

      <div class="q-gutter-sm text-center">
        <q-radio
          v-model="channelType"
          :val="CreateChannelApi.ChannelType.Messages"
          label="Message"
        />
        <q-radio
          v-model="channelType"
          :val="CreateChannelApi.ChannelType.Multimedia"
          label="Multimedia"
        />
      </div>

      <div>
        <q-btn label="Submit" type="submit" color="primary" />
        <q-btn
          label="Cancel"
          type="reset"
          color="primary"
          flat
          class="q-ml-sm"
          @click="event('close')"
        />
      </div>
    </q-form>
  </div>
</template>
