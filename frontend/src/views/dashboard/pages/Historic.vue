<template>
  <v-container
    id="tweets"
    fluid
    tag="section"
  >
    <base-material-card color="info">
      <template v-slot:heading>
        <div class="display-2 font-weight-light">
          <v-icon>mdi-filter</v-icon>
          Filters
        </div>
      </template>

      <v-form>
        <v-container class="py-0">
          <v-row>
            <v-col
              cols="12"
              md="4"
            >
              <md-datepicker
                v-model="dateFrom"
                :md-model-type="Number"
              >
                <label>Date From
                </label>
              </md-datepicker>
            </v-col>
            <v-col
              cols="12"
              md="4"
            >
              <md-datepicker
                v-model="dateTo"
                :md-model-type="Number"
              >
                <label>Date To
                </label>
              </md-datepicker>
            </v-col>
            <v-col
              cols="12"
              class="text-right"
            >
              <v-btn
                color="info"
                class="mr-0"
                @click="loadData()"
              >
                <v-icon class="pr-2">
                  mdi-magnify
                </v-icon>
                Search
              </v-btn>
            </v-col>
          </v-row>
        </v-container>
      </v-form>
    </base-material-card>
    <base-material-card
      v-show="showTable"
      icon="mdi-database"
      title="Tweet Table"
      class="px-5 py-3"
    >
      <v-data-table
        :headers="headers"
        :items="tweetList"
        fixed-header
        height="500px"
        hide-default-footer
        disable-pagination
      />
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
  import TweetService from '@/services/tweet.service.js'
  export default {
    data: () => ({
      showSnackbar: false,
      snackbarColor: 'error',
      snackbarMessage: '',
      headers: [
        {
          text: 'Text',
          align: 'start',
          sortable: false,
          value: 'text',
        },
        {
          text: 'Date',
          align: 'start',
          sortable: false,
          value: 'date',
        },
        {
          text: 'Matching Rules',
          align: 'start',
          sortable: false,
          value: 'matchingRules',
        },
      ],
      tweetList: [],
      dateFrom: Number(new Date()),
      dateTo: Number(new Date()),
      showTable: false,
    }),
    methods: {
      loadData () {
        if (this.dateFrom && this.dateTo) {
          TweetService.getTweetsByDate(this.dateFrom, this.dateTo).then(
            response => {
              this.tweetList = response.data
              this.showTable = true
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
