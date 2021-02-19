<template>
  <div id="maintenance">
    <Navbar />
    <v-row>
      <v-container width="600">
        <v-row dense no-gutters>
          <v-col cols="6" md="4">
            <v-row justify="center" align="center">
              <v-card height="100" width="220" class="mt-7">
                <v-row>
                  <v-col cols="9">
                    <v-row align="center">
                      <span class="mt-8 ml-7 title font-weight-bold">
                        {{ item.TABLESPACE_NAME }}
                      </span>
                    </v-row>
                    <v-row align="center">
                      <v-chip
                        class="ml-7 mt-4 white--text rounded-sm"
                        color="green"
                        label
                        small
                      >
                        status
                      </v-chip>
                      <span class="body-2 mt-4 ml-2">online</span>
                    </v-row>
                  </v-col>
                  <v-col cols="3">
                    <v-icon class="mt-10 mr-7" color="green">
                      published_with_changes
                    </v-icon>
                  </v-col>
                </v-row>
              </v-card>
            </v-row>
            <v-row justify="center" align="center">
              <v-card height="100" width="220" class="mt-7">
                <v-row>
                  <v-col cols="9">
                    <v-row align="center">
                      <span class="mt-8 ml-7 title font-weight-bold">
                        {{
                          Math.round((item.FREE + Number.EPSILON) * 100) / 100
                        }}
                      </span>
                    </v-row>
                    <v-row align="center">
                      <v-chip
                        class="ml-7 mt-4 white--text rounded-sm"
                        color="#8BC34A"
                        label
                        small
                      >
                        {{
                          Math.round((item.PERC_FREE + Number.EPSILON) * 100) /
                          100
                        }}
                        %
                      </v-chip>
                      <span class="body-2 mt-4 ml-2">Free space</span>
                    </v-row>
                  </v-col>
                  <v-col cols="3">
                    <v-icon class="mt-10 mr-7" color="#8BC34A">
                      hourglass_empty
                    </v-icon>
                  </v-col>
                </v-row>
              </v-card>
            </v-row>

            <v-row justify="center" align="center">
              <v-card height="100" width="220" class="mt-7">
                <v-row>
                  <v-col cols="9">
                    <v-row align="center">
                      <span class="mt-9 ml-7 title font-weight-bold">
                        {{ item.CONTENTS }}
                      </span>
                    </v-row>
                    <v-row align="center">
                      <span class="ml-7 mt-4 body-2 font-weight-light"
                        >Contents</span
                      >
                    </v-row>
                  </v-col>
                  <v-col cols="3">
                    <v-icon class="mt-10 mr-7" color="#D32F2F"> source </v-icon>
                  </v-col>
                </v-row>
              </v-card>
            </v-row>
          </v-col>
          <v-col cols="8" md="3">
            <v-row>
              <v-card height="100" width="220" class="mt-7 ml-n16" >
                <v-row>
                  <v-col cols="9">
                    <v-row align="center">
                      <h1 class="mt-8 ml-7">
                        {{
                          Math.round(
                            (item.TABLESPACE_SIZE + Number.EPSILON) * 100
                          ) / 100
                        }}
                      </h1>
                    </v-row>
                    <v-row align="center">
                      <span class="ml-8 mt-2 body-2 font-weight-light"
                        >Megabytes</span
                      >
                    </v-row>
                  </v-col>
                  <v-col cols="3">
                    <v-icon class="mt-10 mr-7" color="#80DEEA">
                      aspect_ratio
                    </v-icon>
                  </v-col>
                </v-row>
              </v-card>
            </v-row>
            <v-row>
              <v-card height="100" width="220" class="mt-7 ml-n16">
                <v-row>
                  <v-col cols="9">
                    <v-row align="center">
                      <span class="mt-8 ml-7 title font-weight-bold">
                        {{
                          Math.round((item.USED + Number.EPSILON) * 100) / 100
                        }}
                      </span>
                    </v-row>
                    <v-row align="center">
                      <v-chip
                        class="ml-7 mt-4 white--text rounded-sm"
                        color="#F9A825"
                        label
                        small
                      >
                        {{
                          Math.round((item.PERC_USED + Number.EPSILON) * 100) /
                          100
                        }}
                        %
                      </v-chip>
                      <span class="body-2 mt-4 ml-2">Used space</span>
                    </v-row>
                  </v-col>
                  <v-col cols="3">
                    <v-icon class="mt-10 mr-7" color="#F9A825">
                      hourglass_full
                    </v-icon>
                  </v-col>
                </v-row>
              </v-card>
            </v-row>

            <v-row>
              <v-card height="100" width="220" class="mt-7 ml-n16">
                <v-row>
                  <v-col cols="9">
                    <v-row align="center">
                      <span class="mt-9 ml-7 title font-weight-bold">
                        {{ item.ALLOCATION_TYPE }}
                      </span>
                    </v-row>
                    <v-row align="center">
                      <span class="body-2 mt-4 ml-7">Allocation Type</span>
                    </v-row>
                  </v-col>
                  <v-col cols="3">
                    <v-icon class="mt-10 mr-7" color="#9575CD">
                      system_update_alt
                    </v-icon>
                  </v-col>
                </v-row>
              </v-card>
            </v-row>
          </v-col>

          <v-col cols="12" md="5" class="ml-n16">
            <v-card class="mt-4 ml-n16" height="332" width="800"
            >
             <stackedTimestamps :width="500" :height="300" :id="$route.params.id"></stackedTimestamps> 
             <!--<apexchart type="bar" ref="chart1" height="350" :options="chartOptions" :series="series"></apexchart>-->

            </v-card>
          </v-col>
        </v-row>
        
      </v-container>
    </v-row>
  </div>
</template>

<script>
import axios from "axios";
import Navbar from "@/components/navBar.vue";
import stackedTimestamps from "@/components/stackedTimestamps"

export default {
  name: "Tablespace",
  props: ["id", "id2"],
  
  data() {
    return {
      item: [],
      timestamps: [],
      categories: [],
      series: [{
            name: 'Free',
            data: []
          }, {
            name: 'Used',
            data: []
          }],
          chartOptions: {
            chart: {
              type: 'bar',
              height: 350,
              stacked: true,
            },
            plotOptions: {
              bar: {
                horizontal: true,
              },
            },
            stroke: {
              width: 1,
              colors: ['#fff']
            },
            title: {
              text: "Tablespace's Timestamp",
           
            },
            xaxis: {
              categories: [],
            },
            yaxis: {
              title: {
                text: undefined
              },
            },
            fill: {
              opacity: 1
            },
            legend: {
              position: 'top',
              horizontalAlign: 'center',
              offsetX: 40
            }
          },
    };
  },
  created: async function () {
    let response = await axios.get(
      "http://localhost:5001/tablespacedata/" + this.id + "/" + this.id2
    );
    this.item = response.data.rows[0];

    let response2 = await axios.get(
            "http://localhost:5001/lasttimestamps/" + this.id 
    ); 

    for (var i = 0; i < response2.data.rows.length; i++){
            this.chartOptions.xaxis.categories.push(response2.data.rows[i].TIMESTAMP);
          } 
  },

  
 
  components: {
    Navbar,
    stackedTimestamps
  },
};
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