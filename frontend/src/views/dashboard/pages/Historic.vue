<template>
  <v-container
    id="tweets"
    fluid
    tag="section"
  >
    <base-material-card
      color="info"
    >
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
              md="3"
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
              md="3"
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
              md="3"
            >
              <v-select
                v-model="matchingRules"
                multiple
                chips
                prepend-icon="mdi-ruler"
                :items="ruleList"
                item-text="tag"
                item-value="tag"
                label="Select one rule"
              />
            </v-col>
            <v-col
              cols="12"
              class="text-right"
            >
              <v-btn
                color="info"
                class="mr-0"
                @click="loadTweets()"
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
      v-if="showTable"
      icon="mdi-database"
      title="Tweet Table"
      class="px-5 py-3"
    >
      <v-data-table
        :headers="headers"
        :items="tweetList"
        :options.sync="options"
        :server-items-length="totalTweets"
        :items-per-page="5"
        :footer-props="footerProps"
        fixed-header
        height="400px"
      >
        <template v-slot:[`item.matchingRules`]="{ item }">
          <v-chip
            v-for="rule in item.matchingRules"
            :key="rule"
            class="ma-2"
            color="primary"
          >
            {{ rule }}
          </v-chip>
        </template>
      </v-data-table>
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
  import ConnectorService from '@/services/connector.service.js'
  export default {
    data: () => ({
      matchingRules: null,
      ruleList: [],
      footerProps: { 'items-per-page-options': [5, 10, 20, 30] },
      totalTweets: 0,
      options: {},
      loading: false,
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
    watch: {
      options: {
        handler () {
          this.loadData()
        },
      },
    },
    created () {
      this.loadRules()
    },
    methods: {
      loadRules () {
        ConnectorService.getRules().then(
          response => {
            this.ruleList = response.data.data
          },
        )
      },
      loadTweets () {
        this.loadData()
        this.showTable = true
      },
      loadData () {
        const { page, itemsPerPage } = this.options
        if (this.dateFrom && this.dateTo && page) {
          this.loading = true
          TweetService.countByDate(this.dateFrom, this.dateTo, this.matchingRules).then(
            response => {
              this.totalTweets = response.data
            },
          )
          TweetService.getTweetsByDate(this.dateFrom, this.dateTo, this.matchingRules, page - 1, itemsPerPage).then(
            response => {
              this.tweetList = response.data
              this.loading = false
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
