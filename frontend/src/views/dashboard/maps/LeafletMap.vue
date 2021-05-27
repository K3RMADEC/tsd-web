<template>
  <v-container
    id="map"
    fluid
    tag="section"
  >
    <v-row>
      <v-col>
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
                  md="6"
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
                  md="6"
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
                  md="6"
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
                  md="6"
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
      </v-col>
      <v-col>
        <base-material-card color="success">
          <template v-slot:heading>
            <div class="display-2 font-weight-light">
              <v-icon>mdi-twitter</v-icon>
              Tweet Detail
            </div>
          </template>
          <v-card-text v-if="!tweetDetail">
            Click one tweet to display the details
          </v-card-text>
          <v-card-text v-else>
            <p><v-icon>mdi-calendar-clock</v-icon> {{ tweetDetail.date }}</p>
            <p><v-icon>mdi-ruler</v-icon> {{ tweetDetail.matchingRules }}</p>
            <p> {{ tweetDetail.text }}</p>
            <a
              target="_blank"
              :href="'https://twitter.com/ProjectTsd/status/'+tweetDetail.tweetId"
            >Show in Twitter</a>
          </v-card-text>
        </base-material-card>
      </v-col>
    </v-row>

    <v-map
      v-if="showMap"
      style="height: 80%;"
      :zoom="2"
      :center="initialLocation"
    >
      <v-icondefault />
      <v-tilelayer url="http://{s}.tile.osm.org/{z}/{x}/{y}.png" />
      <v-marker-cluster
        ref="clusterRef"
        :options="clusterOptions"
        @clusterclick="click()"
        @ready="ready"
      >
        <v-marker
          v-for="tweet in tweetList"
          :key="tweet.tweetId"
          :lat-lng="[tweet.coordinates.lat, tweet.coordinates.lon]"
          :icon="icon"
          @click="showDetail(tweet)"
        >
          <v-popup :content="'Tweet ID: '+tweet.tweetId" />
        </v-marker>
      </v-marker-cluster>
    </v-map>
  </v-container>
</template>

<script>
  import * as Vue2Leaflet from 'vue2-leaflet'
  import { latLng, Icon, icon } from 'leaflet'
  import Vue2LeafletMarkercluster from './Vue2LeafletMarkercluster'
  import iconUrl from 'leaflet/dist/images/marker-icon.png'
  import shadowUrl from 'leaflet/dist/images/marker-shadow.png'
  import TweetService from '@/services/tweet.service.js'
  import ConnectorService from '@/services/connector.service.js'

  export default {
    components: {
      'v-map': Vue2Leaflet.LMap,
      'v-tilelayer': Vue2Leaflet.LTileLayer,
      'v-icondefault': Vue2Leaflet.LIconDefault,
      'v-marker': Vue2Leaflet.LMarker,
      'v-popup': Vue2Leaflet.LPopup,
      'v-marker-cluster': Vue2LeafletMarkercluster,
    },
    data () {
      const customicon = icon(Object.assign({},
                                            Icon.Default.prototype.options,
                                            { iconUrl, shadowUrl },
      ))
      return {
        matchingRules: null,
        ruleList: [],
        tweetDetail: null,
        tweetList: [],
        showMap: false,
        dateFrom: Number(new Date()),
        dateTo: Number(new Date()),
        icon: customicon,
        clusterOptions: {},
        initialLocation: latLng(10.579401825153555, -29.791665618740296),
      }
    },
    mounted () {
      setTimeout(() => {
        console.log('done')
        this.$nextTick(() => {
          this.clusterOptions = { disableClusteringAtZoom: 11 }
        })
      }, 5000)
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
      click: (e) => console.log('clusterclick', e),
      ready: (e) => console.log('ready', e),
      loadData () {
        if (this.dateFrom && this.dateTo) {
          TweetService.getGeoTweetsByDate(this.dateFrom, this.dateTo, this.matchingRules).then(
            response => {
              this.tweetList = response.data
              this.$refs.clusterRef.mapObject.refreshClusters()
              this.showMap = true
            },
          )
        }
      },
      showDetail (tweet) {
        this.tweetDetail = tweet
      },
      createSnackbar (color, text) {
        this.snackbarColor = color
        this.snackbarMessage = text
        this.showSnackbar = true
      },
    },
  }
</script>

<style>
  @import "~leaflet/dist/leaflet.css";
  @import "~leaflet.markercluster/dist/MarkerCluster.css";
  @import "~leaflet.markercluster/dist/MarkerCluster.Default.css";
  html, body {
    height: 100%;
    margin: 0;
  }

  .md-datepicker-dialog {
    z-index: 1000;
  }
  .v-menu__content {
    z-index: 1000 !important;
  }
</style>
