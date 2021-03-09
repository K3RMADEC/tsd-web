<template>
  <v-container
    id="users"
    fluid
    tag="section"
  >
    <base-material-card
      icon="mdi-account"
      title="User Table"
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
            v-for="(user, i) in users"
            :key="i"
          >
            <td>{{ user.username }}</td>
            <td>{{ user.role }}</td>
            <td>
              <v-btn
                icon
                color="red"
                @click="deleteUser(user.username)"
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
                  mdi-account-plus
                </v-icon>
                Create
              </v-btn>
            </template>
            <v-card>
              <v-card-title>
                <span class="headline">Create New User</span>
              </v-card-title>
              <v-card-text>
                <v-form>
                  <v-text-field
                    v-model="newUser.username"
                    label="Username"
                    name="user"
                    prepend-icon="mdi-account"
                    type="text"
                  />
                  <v-text-field
                    id="password"
                    v-model="newUser.password"
                    :append-icon="showPassword ? 'mdi-eye-off' : 'mdi-eye'"
                    :type="showPassword ? 'text' : 'password'"
                    label="Password"
                    name="password"
                    prepend-icon="mdi-lock"
                    @click:append="showPassword = !showPassword"
                  />

                  <v-text-field
                    id="password"
                    v-model="newUser.password2"
                    :append-icon="showPassword2 ? 'mdi-eye-off' : 'mdi-eye'"
                    :type="showPassword2 ? 'text' : 'password'"
                    label="Confirm Password"
                    name="password"
                    prepend-icon="mdi-lock"
                    @click:append="showPassword2 = !showPassword2"
                  />
                  <v-select
                    v-model="newUser.role"
                    prepend-icon="mdi-card-account-details"
                    :items="roleList"
                    label="Selecciona un rol"
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
                  @click="saveUser()"
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
  import UserService from '@/services/user.service.js'
  export default {
    data: () => ({
      showSnackbar: false,
      snackbarColor: 'error',
      snackbarMessage: '',
      dialog: false,
      newUser: {
        username: '',
        password: '',
        password2: '',
        role: 'USER',
      },
      showPassword: false,
      showPassword2: false,
      headers: ['Username', 'Role', ''],
      users: [],
      roleList: ['ADMIN', 'USER'],
    }),
    created () {
      this.loadUsers()
    },
    methods: {
      loadUsers () {
        UserService.getAll().then(
          response => {
            this.users = response.data
          },
        )
      },
      closeDialog () {
        this.dialog = false
        this.newUser = {
          username: '',
          password: '',
          password2: '',
          role: 'USER',
        }
        this.showPassword = false
        this.showPassword2 = false
      },
      saveUser () {
        if (this.newUser.username && this.newUser.password && this.newUser.role) {
          UserService.register(this.newUser)
            .then(
              response => {
                this.createSnackbar('success', 'Usuario creado correctamente')
                this.loadUsers()
                this.closeDialog()
              },
              error => {
                this.createSnackbar('error', 'Error al crear usuario - ' + error.data)
              },
            )
        }
      },
      deleteUser (username) {
        if (username) {
          UserService.remove(username)
            .then(
              response => {
                this.createSnackbar('success', 'Usuario eliminado correctamente')
                this.loadUsers()
              },
              error => {
                this.createSnackbar('error', 'Error al eliminar usuario - ' + error.data)
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
