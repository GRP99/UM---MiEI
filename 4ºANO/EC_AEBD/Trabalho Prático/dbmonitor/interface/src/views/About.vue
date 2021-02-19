<template>
    <div id = "maintenance" class="back">
        <Navbar/>
        <v-card class = "mx-auto justify-center" color= "transparent" flat>
            <v-row justify="center" align="start">
                <v-col cols = "4">
                    <v-card class = "mx-auto mt-4 text-center justify-center" flat color = "transparent">
                        <v-card-title class ="headline justify-center font-weight-medium">DBMonitor - Grupo 02</v-card-title>
                        <v-card-subtitle class="subtitle-1 ">Projeto da Unidade Curricular de <strong>Administração e Exploração de Bases de Dados</strong> (2020/2021)</v-card-subtitle>
                    </v-card>
                </v-col>
            </v-row>

            <v-row align="center" justify="center" >
                <v-col cols = "3">
                    <v-row>
                        <v-card flat class = "mx-auto mt-16" color = "transparent">
                            <v-img
                                src="@/assets/a83732.png"
                                min-height="205"
                                min-width="205"
                            />
                        </v-card>
                    </v-row>

                    <v-row justify="center">
                        <v-card class = "mx-auto mt-4 ml-10 justify-center" flat color = "transparent">
                            <v-card-title class="ml-16 headline font-weight-light">Gonçalo Pinto</v-card-title>
                            <v-card-text class="text-center ml-7">a83732</v-card-text>

                        </v-card>
                    </v-row>
                </v-col>

                <v-col cols = "3">
                    <v-row>
                        <v-card flat class = "mx-auto mt-16" color = "transparent">
                            <v-img
                                src="@/assets/a80791.png"
                                min-height="205"
                                min-width="205"
                            />
                        </v-card>
                    </v-row>

                   <v-row justify="center">
                        <v-card class = "mx-auto mt-4 justify-center pl-4 ml-11 " flat color = "transparent">
                            <v-card-title class="ml-14 headline font-weight-light">João Mota</v-card-title>
                            <v-card-text class="text-center ml-7">a80791</v-card-text>

                        </v-card> 
                        
                    </v-row>
                </v-col>

                <v-col cols = "3">
                    <v-row>
                        <v-card flat class = "mx-auto mt-16 " color = "transparent">
                            <v-img
                                src="@/assets/a84829.png"
                                min-height="200"
                                min-width="200"
                            />
                        </v-card>
                    </v-row>

                   <v-row justify="center">
                        <v-card class = "mx-auto mt-4 justify-center pl-4 ml-0 " flat color = "transparent">
                            <v-card-title class="ml-14 headline font-weight-light">José Gonçalo Costa</v-card-title>
                            <v-card-text class="text-center ml-7">a84829</v-card-text>
                        </v-card> 
                    </v-row>
                </v-col>


                 <v-col cols = "3">
                    <v-row>
                        <v-card flat class = "mx-auto mt-16" color = "transparent">
                            <v-img
                                src="@/assets/PG42839.png"
                                max-height="200"
                                max-width="200"
                            />
                        </v-card>
                    </v-row>

                   <v-row justify="center">
                        <v-card class = "mx-auto mt-4 justify-center pl-4 ml-3 " flat color = "transparent">
                            <v-card-title class="ml-14 headline font-weight-light">José Nuno Costa</v-card-title>
                          <v-card-text class="text-center ml-7">PG42839</v-card-text>

                        </v-card> 
                    </v-row>
                </v-col>
            </v-row>

            <!--<v-divider inset ></v-divider>-->

            <v-row>
                <v-card class = "mx-auto" flat height = "100">

                </v-card>
            </v-row>
        </v-card>
    </div>
</template>

<script>
import Navbar from "@/components/navBar.vue";
export default {
    data: () => ({
    }),
    components: {
        Navbar,
    }, 
}
</script>

<style>
#maintenance {
  background-color: #efdcc3;
  height: 100%;
  background-position: center;
  background-repeat: repeat;
  background-size: contain;
  position: relative;
}
#letra {
  font-family: "Merienda", Helvetica, Arial;
}
</style>

