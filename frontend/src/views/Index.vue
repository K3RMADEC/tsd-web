<template>
  <v-app>
    <dashboard-core-app-bar v-if="!$route.meta.public" />

    <dashboard-core-drawer v-if="!$route.meta.public" />

    <dashboard-core-view />
  </v-app>
</template>

<script>
  export default {
    name: 'DashboardIndex',

    components: {
      DashboardCoreAppBar: () => import('./dashboard/components/core/AppBar'),
      DashboardCoreDrawer: () => import('./dashboard/components/core/Drawer'),
      DashboardCoreView: () => import('./dashboard/components/core/View'),
    },

    data: () => ({
      expandOnHover: false,
    }),
  }
</script>
