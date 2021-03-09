<template>
  <v-container
    id="login"
    class="fill-height"
    fluid
    tag="section"
    :style="{ backgroundImage: 'url(' + require('@/assets/login.jpg') + ')' }"
    style="background-size: cover; background-position: center"
  >
    <v-row
      align="center"
      justify="center"
    >
      <v-col
        cols="12"
        sm="8"
        md="4"
      >
        <v-card
          class="elevation-12"
          style="background-color: rgba(0, 0, 0, 0.82)"
        >
          <v-card-title class="ma-2 d-flex justify-center">
            <v-avatar class="mr-2">
              <v-img
                src="/favicon.png"
              />
            </v-avatar>
            {{ profile.title }}
          </v-card-title>
          <v-card-subtitle class="ma-2">
            Login
          </v-card-subtitle>
          <v-card-text>
            <v-form>
              <v-text-field
                v-model="user.username"
                label="Username"
                name="user"
                prepend-icon="mdi-account"
                type="text"
              />
              <v-text-field
                id="password"
                v-model="user.password"
                :append-icon="showPassword ? 'mdi-eye-off' : 'mdi-eye'"
                :type="showPassword ? 'text' : 'password'"
                label="Password"
                name="password"
                prepend-icon="mdi-lock"
                @keydown.enter="login"
                @click:append="showPassword = !showPassword"
              />
            </v-form>
          </v-card-text>
          <v-card-actions>
            <div class="flex-grow-1" />
            <v-btn
              :disabled="loading"
              @click="login"
            >
              Acceder
              <v-icon right>
                mdi-play-circle-outline
              </v-icon>
            </v-btn>
          </v-card-actions>
          <div class="form-group">
            <div
              v-if="message"
              class="alert alert-danger"
              role="alert"
            >
              {{ message }}
            </div>
          </div>
        </v-card>
      </v-col>
    </v-row>
  </v-container>
</template>

<script>
  export default {
    data: () => ({
      user: {
        password: null,
        username: null,
      },
      showPassword: null,
      loading: false,
      message: '',
    }),
    computed: {
      profile () {
        return {
          avatar: true,
          title: this.$t('avatar'),
        }
      },
      loggedIn () {
        return this.$store.state.auth.status.loggedIn
      },
    },
    methods: {
      login () {
        this.loading = true

        if (this.user.username && this.user.password) {
          this.$store.dispatch('auth/login', this.user).then(
            () => {
              this.$router.push('/dashboard')
            },
            error => {
              this.loading = false
              this.message =
                (error.response && error.response.data) ||
                error.message ||
                error.toString()
            },
          )
        }
        this.loading = false
      },
    },
  }
</script>

<style>
</style>
