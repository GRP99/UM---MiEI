<template>
  <v-footer dark padless absolute>
    <v-card
      flat
      tile
      class="grey darken-4 white--text text-center mx-auto"
      width="1500"
    >
      <v-img class="ma-4" src="@/assets/logoUM.png" height="110" width="120">
      </v-img>

      <v-card-text class="white--text mx-auto">
        <v-row>
          <v-col class="ml-16" cols="2"> Gonçalo Rodrigues Pinto, A83732 </v-col>
          <v-col class="ml-5" cols="3"> João Diogo Mendes Teixeira da Mota, A80791 </v-col>
          <v-col class="ml-5" cols="3"> José Gonçalo Macedo Costa, PG42839 </v-col>
          <v-col class="ml-5" cols="3"> José Nuno Martins da Costa, A84829 </v-col>
        </v-row>
      </v-card-text>

      <v-divider></v-divider>

      <v-card-text class="white--text">
        {{ new Date().getFullYear() }} —
        <strong>Administração e Exploração de Bases de Dados</strong>
      </v-card-text>
    </v-card>
  </v-footer>
</template>

