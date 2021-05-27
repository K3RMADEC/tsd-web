<template>
  <v-container
    id="connector"
    fluid
    tag="section"
  >
    <base-material-card
      color="info"
      title="Connector"
      class="px-5 py-3"
    >
      <v-row
        class="ma-2"
      >
        <v-col md="2">
          <h1>
            <!-- 0 open, 1 close, 2 interrupt -->
            Status: <v-icon :color="streamStatus == 1 ? 'red' : streamStatus == 0 ? 'green' : 'yellow'">
              mdi-checkbox-blank-circle
            </v-icon>
          </h1>
        </v-col>
        <v-col md="1">
          <v-btn
            fab
            small
            @click="loadStreamStatus()"
          >
            <v-icon>
              mdi-refresh
            </v-icon>
          </v-btn>
        </v-col>
        <v-col md="1">
          <v-btn
            fab
            small
            @click="startStream()"
          >
            <v-icon color="green">
              mdi-play-circle-outline
            </v-icon>
          </v-btn>
        </v-col>
        <v-col md="1">
          <v-btn
            fab
            small
            @click="stopStream()"
          >
            <v-icon color="red">
              mdi-stop-circle-outline
            </v-icon>
          </v-btn>
        </v-col>
      </v-row>
    </base-material-card>
    <base-material-card
      icon="mdi-ruler"
      title="Rule Table"
      class="px-5 py-3"
    >
      <v-simple-table
        fixed-header
        height="300px"
      >
        <thead>
          <tr>
            <th
              v-for="(header, i) in headers"
              :key="i"
              class="primary--text"
            >
              {{ header }}
            </th>
          </tr>
        </thead>

        <tbody>
          <tr
            v-for="(rule, i) in rules"
            :key="i"
          >
            <td>{{ rule.tag }}</td>
            <td>{{ rule.value }}</td>
            <td>
              <v-btn
                icon
                color="red"
                @click="deleteRule(rule.id)"
              >
                <v-icon>
                  mdi-trash-can-outline
                </v-icon>
              </v-btn>
            </td>
          </tr>
        </tbody>
      </v-simple-table>
      <v-row justify="center">
        <v-col md="2">
          <v-dialog
            v-model="dialog"
            persistent
            max-width="600px"
          >
            <template v-slot:activator="{ on, attrs }">
              <v-btn
                color="primary"
                v-bind="attrs"
                v-on="on"
              >
                <v-icon class="mr-2">
                  mdi-plus-circle
                </v-icon>
                Create
              </v-btn>
            </template>
            <v-card>
              <v-card-title>
                <span class="headline">Create New Rule</span>
              </v-card-title>
              <v-card-text>
                <v-form>
                  <v-text-field
                    v-model="newRule.tag"
                    label="Tag"
                    type="text"
                  />
                  <v-text-field
                    v-model="newRule.value"
                    label="Value"
                    type="text"
                  />
                </v-form>
              </v-card-text>
              <v-card-actions>
                <v-spacer />
                <v-btn
                  color="blue darken-1"
                  text
                  @click="closeDialog()"
                >
                  Close
                </v-btn>
                <v-btn
                  color="blue darken-1"
                  text
                  @click="saveRule()"
                >
                  Save
                </v-btn>
              </v-card-actions>
            </v-card>
          </v-dialog>
        </v-col>
      </v-row>
    </base-material-card>
    <base-material-snackbar
      v-model="showSnackbar"
      :type="snackbarColor"
      v-bind="{
        'bottom': true,
        'right': true,
      }"
    >
      {{ snackbarMessage }}
    </base-material-snackbar>
  </v-container>
</template>

<script>
  import ConnectorService from '@/services/connector.service.js'
  export default {
    data: () => ({
      showSnackbar: false,
      snackbarColor: 'error',
      snackbarMessage: '',
      dialog: false,
      newRule: {
        tag: '',
        value: '',
      },
      headers: ['Tag', 'Value', ''],
      rules: [],
      streamStatus: 1, // 0 open, 1 close, 2 interrupt
    }),
    created () {
      this.loadRules()
      this.loadStreamStatus()
    },
    methods: {
      startStream () {
        ConnectorService.startStream().then(
          response => {
            this.snackbarColor = 'success'
            this.snackbarMessage = response.data.text
          },
          error => {
            this.snackbarColor = 'error'
            this.snackbarMessage = error.data.text
          },
        )
        this.showSnackbar = true
        this.loadStreamStatus()
      },
      stopStream () {
        ConnectorService.stopStream().then(
          response => {
            this.snackbarColor = 'success'
            this.snackbarMessage = response.data.text
          },
          error => {
            this.snackbarColor = 'error'
            this.snackbarMessage = error.data.text
          },
        )
        this.showSnackbar = true
        this.loadStreamStatus()
      },
      loadStreamStatus () {
        ConnectorService.getStreamStatus().then(
          response => {
            this.streamStatus = response.data
          },
        )
      },
      loadRules () {
        ConnectorService.getRules().then(
          response => {
            this.rules = response.data.data
          },
        )
      },
      closeDialog () {
        this.dialog = false
        this.newRule = {
          tag: '',
          value: '',
        }
      },
      saveRule () {
        if (this.newRule.tag && this.newRule.value) {
          ConnectorService.createRule(this.newRule)
            .then(
              response => {
                this.createSnackbar('success', response.data.text)
                this.loadRules()
                this.closeDialog()
              },
              error => {
                this.createSnackbar('error', error.response.data.text + ' - ' + error.response.data.detail)
              },
            )
        }
      },
      deleteRule (ruleId) {
        if (ruleId) {
          ConnectorService.deleteRule(ruleId)
            .then(
              response => {
                this.createSnackbar('success', 'Rule successfully removed.')
                this.loadRules()
              },
              error => {
                this.createSnackbar('error', 'Error. Rule not removed - ' + error.data)
              },
            )
        }
      },
      createSnackbar (color, text) {
        this.snackbarColor = color
        this.snackbarMessage = text
        this.showSnackbar = true
      },
    },
  }
</script>
