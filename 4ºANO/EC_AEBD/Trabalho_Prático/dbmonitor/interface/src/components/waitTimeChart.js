import axios from 'axios';
import {Bar} from "vue-chartjs";
import moment from 'moment/moment'


export default {
    extends: Bar,
    props: ['id'],

    data: () => (
        {
            type: 'Bar',
            chartdata: {
                labels: [],
                datasets: [
                    {
                        label: 'Wait time (Micro)',
                        backgroundColor: 'rgba(255,145,0, 0.2)',
                        borderColor: 'rgba(255,145,0, 1)',
                        data: [
                            0,
                            0,
                            0,
                            0,
                            0
                        ],
                        fill: false,
                        borderWidth: 1,
                        categoryPercentage: 1.0,
                        barPercentage: 0.5
                    },
                ]
            },
            options: {
                beginAtZero: true,
                title: {
                    display: true,
                    text: "WAIT TIME MICRO",
                    position: "top",
                    fontSize: "15",
                    fontStyle: "light",
                    lineHeight: 0.5
                },
                responsive: true,
                maintainAspectRatio: false,
                elements: {
                    line: {
                        tension: 0 // disables bezier curves
                    }
                },
                scales: {
                    xAxes: [
                        {
                            gridLines: {
                                offsetGridLines: false
                            },
                            beginAtZero: true

                        }
                    ],
                    yAxes: [
                        {
                            gridLines: {
                                offsetGridLines: false
                            }

                        }
                    ]
                },
                legend: {
                    align: 'center',
                    fontFamily: "'Merienda', 'Helvetica', 'Arial'"
                },
                layout: {
                    padding: {
                        left: 20,
                        right: 20,
                        top: 10,
                        bottom: 10
                    }
                }
            }
        }
    ),

    async mounted() {
        let response2 = await axios.get("http://localhost:5001/getSessionsTimestampUser/" + this.id);

        for (var i = 0; i < response2.data.rows.length; i++) {
            this.chartdata.labels.push(moment(response2.data.rows[i].TIMESTAMP).format('MMM DD, YYYY HH:mm:ss'));
            this.chartdata.datasets[0].data[i] = response2.data.rows[i].WAIT_TIME_MICRO;
        }

        this.renderChart(this.chartdata, this.options)
    }
};
