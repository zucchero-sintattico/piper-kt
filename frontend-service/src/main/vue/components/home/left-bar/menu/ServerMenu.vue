<script setup lang="ts">
import { computed, ref, watch, type Ref, onMounted } from 'vue'
import HorizontalChannel from '../horizontal-component/HorizontalChannel.vue'
import HorizontalUser from '../horizontal-component/HorizontalUser.vue'
import { CreateChannelApi, GetChannelByIdApi } from '@api/piperchat/channel'
import { useUserStore } from '@/stores/user'
import { useServerStore } from '@/stores/server'
import BottomPopUp from '@/components/utils/BottomPopUp.vue'
import { BannerColor } from '@/components/utils/BannerColor'
import { useAppStore } from '@/stores/app'

const appStore = useAppStore()
const userStore = useUserStore()
const serverStore = useServerStore()

const props = defineProps(['active'])
const emit = defineEmits(['update:active'])

const active = computed({
  get() {
    return props.active
  },
  set() {
    emit('update:active')
  }
})

const wantToDeleteChannel = ref(false)
const channelToDelete: Ref<GetChannelByIdApi.Responses.Channel | undefined> = ref(undefined)
function setChannelToDelete(channel: GetChannelByIdApi.Responses.Channel) {
  channelToDelete.value = channel
  wantToDeleteChannel.value = true
}
async function deleteSelectedChannel() {
  try {
    await serverStore.deleteChannel(appStore.selectedServer!.id, channelToDelete.value!.id)
    popUpBanner('Channel deleted', BannerColor.OK)
  } catch (error) {
    popUpBanner(String(error), BannerColor.ERROR)
  }
}

const confirmKickUser = ref(false)
const userToKick = ref('')
function setUserToKick(user: string) {
  userToKick.value = user
  confirmKickUser.value = true
}
async function kickSelectedUser() {
  try {
    await serverStore.kickUser(appStore.selectedServer!.id, userToKick.value)
    popUpBanner('User kicked', BannerColor.OK)
  } catch (error) {
    popUpBanner(String(error), BannerColor.ERROR)
  }
}

const serverTab = ref('setting')

const BANNER_TIMEOUT = 3000
const resultBanner = ref(false)
const colorBanner = ref(BannerColor.OK)
const contentBanner = ref('')
function popUpBanner(content: string, color: BannerColor) {
  contentBanner.value = content
  colorBanner.value = color
  resultBanner.value = true
  setTimeout(() => {
    resultBanner.value = false
  }, BANNER_TIMEOUT)
}

function validateText(text: string) {
  // check if is emt or only white space
  return text.trim().length > 0
}

const updatedServerInfo = ref({ name: '', description: '' })
async function updateServerName(serverName: string) {
  if (!validateText(serverName)) {
    popUpBanner('Server name cannot be empty', BannerColor.ERROR)
    return
  }
  try {
    await serverStore.updateServer(appStore.selectedServer!.id, serverName)
    popUpBanner('Server name updated', BannerColor.OK)
  } catch (error) {
    popUpBanner(String(error), BannerColor.ERROR)
  }
}
async function updateServerDescription(serverDescription: string) {
  if (!validateText(serverDescription)) {
    popUpBanner('Server description cannot be empty', BannerColor.ERROR)
    return
  }
  try {
    await serverStore.updateServer(appStore.selectedServer!.id, undefined, serverDescription)
    popUpBanner('Server description updated', BannerColor.OK)
  } catch (error) {
    popUpBanner(String(error), BannerColor.ERROR)
  }
}

async function copyServerId() {
  navigator.clipboard.writeText(appStore.selectedServer!.id)
  popUpBanner('Server id copied', BannerColor.OK)
}

watch(
  () => appStore.selectedServer,
  () => {
    console.log('selected server changed')
    updatedServerInfo.value.name = appStore.selectedServer?.name as string
    updatedServerInfo.value.description = appStore.selectedServer?.description as string
  }
)
onMounted(() => {
  updatedServerInfo.value.name = appStore.selectedServer?.name as string
  updatedServerInfo.value.description = appStore.selectedServer?.description as string
})
</script>

<template>
  <div>
    <!-- start Server pop up -->
    <q-dialog v-model="active">
      <q-card class="container">
        <q-tabs
          v-model="serverTab"
          dense
          class="text-grey"
          active-color="primary"
          indicator-color="primary"
          align="justify"
          narrow-indicator
        >
          <q-tab name="setting" label="Settings" />
          <q-tab name="channels" label="Channels" />
          <q-tab name="partecipants" label="Partecipants" />
        </q-tabs>

        <q-separator />

        <q-tab-panels v-model="serverTab" animated>
          <!-- start Setting Tab -->
          <q-tab-panel name="setting">
            <div class="text-h4">Server Settings</div>
            <div class="row">
              <div class="text-h6 q-pa-sm">Name:</div>
              <!-- make readonly if the user is't the owner -->
              <q-input
                v-model="updatedServerInfo.name"
                label="Server name"
                outlined
                class="q-mb-md"
                :readonly="!serverStore.amITheOwner"
              />
              <q-btn
                label="Save"
                color="primary"
                class="q-mb-md"
                v-if="serverStore.amITheOwner"
                @click="updateServerName(updatedServerInfo.name)"
              />
            </div>
            <div class="row">
              <div class="text-h6 q-pa-sm">Owner:</div>
              <div class="text-to-copy q-pa-sm text-h6">
                {{ appStore.selectedServer?.owner }}
              </div>
            </div>
            <div class="row">
              <div class="text-h6 q-pa-sm">Description:</div>
              <q-input
                v-model="updatedServerInfo.description"
                label="Server description"
                outlined
                class="q-mb-md"
                type="textarea"
                :readonly="!serverStore.amITheOwner"
              />
              <!-- submit button -->
              <q-btn
                label="Save"
                color="primary"
                class="q-mb-md"
                v-if="serverStore.amITheOwner"
                @click="updateServerDescription(updatedServerInfo.description)"
              />
            </div>
            <div class="row">
              <div class="text-h6 q-pa-sm">Server Id:</div>
              <div class="text-to-copy q-pa-sm text-h6 bg-accent inset-shadow-down">
                {{ appStore.selectedServer?.id }}
              </div>
              <q-btn round class="q-ml-md q-mb-md" icon="content_copy" @click="copyServerId" />
            </div>

            <!-- TODO -->
          </q-tab-panel>
          <!-- end Setting Tab -->

          <!-- start Channels Tab -->
          <q-tab-panel name="channels">
            <div class="text-h4">Channels</div>
            <q-list v-for="channel in appStore.selectedServer?.channels" :key="channel.id">
              <q-item>
                <q-item-section style="font-size: 1.5em">
                  <HorizontalChannel
                    clickable
                    :name="channel.name"
                    :icon="
                      channel.channelType == CreateChannelApi.ChannelType.Messages
                        ? 'chat'
                        : 'volume_up'
                    "
                    class="q-pa-sm"
                  />
                </q-item-section>
                <!-- start Cancel button -->
                <q-btn
                  style="width: fit-content"
                  icon="close"
                  class="bg-red text-white"
                  v-if="serverStore.amITheOwner"
                  @click="setChannelToDelete(channel)"
                />
                <!-- start Cancel button -->
              </q-item>
            </q-list>
          </q-tab-panel>
          <!-- end Channels Tab -->

          <!-- start Partecipants Tab -->
          <q-tab-panel name="partecipants">
            <div class="text-h4">Partecipants</div>
            <!-- Display only partecipants, not the owner -->
            <q-list
              v-for="user in appStore.selectedServer?.users.filter(
                (p) => p !== userStore.username
              )"
              :key="user"
            >
              <q-item>
                <q-item-section style="font-size: 1.5em">
                  <HorizontalUser :name="user" class="q-pa-sm" />
                </q-item-section>
                <!-- start Kick button -->
                <q-btn
                  style="width: fit-content"
                  icon="close"
                  class="bg-red text-white"
                  v-if="serverStore.amITheOwner"
                  @click="setUserToKick(user)"
                />
                <!-- start Kick button -->
              </q-item>
            </q-list>
          </q-tab-panel>
          <!-- end Partecipants Tab -->
        </q-tab-panels>
      </q-card>
    </q-dialog>
    <!-- end Friends pop up -->

    <!-- start Confirm pop up -->
    <q-dialog v-model="wantToDeleteChannel">
      <q-card>
        <q-card-section class="row items-center">
          <q-avatar icon="delete" color="black" text-color="white" />
          <span class="q-ml-sm"
            >Are you sure to delete
            <strong>{{ channelToDelete?.name }}</strong>
            channel?
          </span>
        </q-card-section>

        <q-card-actions align="center">
          <q-btn
            label="I want to delete the channel"
            color="red"
            v-close-popup
            @click="deleteSelectedChannel"
          />
        </q-card-actions>
      </q-card>
    </q-dialog>
    <!-- end Confirm pop up -->

    <!-- start Confirm pop up -->
    <q-dialog v-model="confirmKickUser">
      <q-card>
        <q-card-section class="row items-center">
          <q-avatar icon="delete" color="black" text-color="white" />
          <span class="q-ml-sm">
            Are you sure to kick
            <strong>{{ userToKick }}</strong>
            ?
          </span>
        </q-card-section>

        <q-card-actions align="center">
          <q-btn
            label="I want to kick the user"
            color="red"
            v-close-popup
            @click="kickSelectedUser"
          />
        </q-card-actions>
      </q-card>
    </q-dialog>
    <!-- end Confirm pop up -->

    <BottomPopUp v-model:active="resultBanner" :content="contentBanner" :color="colorBanner" />
  </div>
</template>

<style scoped lang="scss">
.container {
  min-width: 30vw;
}

.text-to-copy {
  border-radius: 10px !important;
  font-family: monospace !important;
}

@media screen and (max-width: 1200px) {
  .container {
    min-width: 80vw;
  }
}
</style>
