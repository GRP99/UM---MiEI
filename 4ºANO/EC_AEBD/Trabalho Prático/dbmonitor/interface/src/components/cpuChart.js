import {Line} from "vue-chartjs";
import moment from 'moment/moment';
import axios from 'axios';


export default {
    extends: Line,
    props: [
        'id',
    ],
    data: () => (
        {
            chartdata: {
                labels: [],
                datasets: [
                    {
                        label: "USAGE",
                        data: [],
                        backgroundColor: "transparent",
                        borderColor: "rgba(255,0,0,0.25)",
                        pointBackgroundColor: "white"
                    }
                ]
            },
            options: {
                scales: {
                    yAxes: [
                        {
                            ticks: {
                                beginAtZero: true
                            }
                        }
                    ]
                },
                responsive: true,
                maintainAspectRatio: false,
                title: {
                    display: true,
                    text: "CPU Usage",
                    fontSize: 20
                }
            }
        }
    ),
    async mounted() {
        let response2 = await axios.get("http://localhost:5001/getCPU/" + this.id);


        for (var i = 0; i < response2.data.rows.length; i++) {
            this.chartdata.labels.push(moment(response2.data.rows[i].TIMESTAMP).format('MMM DD, YYYY HH:mm:ss'));
            this.chartdata.datasets[0].data[i] = response2.data.rows[i].USAGE;
        }

        this.renderChart(this.chartdata, this.options)
    }
};
