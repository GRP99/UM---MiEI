import axios from 'axios';
import {HorizontalBar} from "vue-chartjs";
import moment from 'moment/moment'


export default {
    extends: HorizontalBar,
    props: ['id'],

    data: () => (
        {
            type: 'horizontalBar',
            chartdata: {
                labels: [],
                datasets: [
                    {
                        label: 'Free',
                        backgroundColor: 'rgba(100,221,23, 0.4)',
                        borderColor: 'rgb(100,221,23)',
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
                    }, {
                        label: 'Used',
                        backgroundColor: 'rgba(255,145,0, 0.2)',
                        borderColor: 'rgb(255,145,0)',
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
                    text: "TABLESPACE'S TIMESTAMPS OCCUPATION",
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
                            stacked: true,
                            gridLines: {
                                offsetGridLines: false
                            },
                            beginAtZero: true

                        }
                    ],
                    yAxes: [
                        {
                            stacked: true,
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
        let response2 = await axios.get("http://localhost:5001/lasttimestamps/" + this.id);

        for (var i = 0; i < response2.data.rows.length; i++) {
            this.chartdata.labels.push(moment(response2.data.rows[i].TIMESTAMP).format('MMM DD, YYYY HH:mm:ss'));
            this.chartdata.datasets[0].data[i] = response2.data.rows[i].FREE;
            this.chartdata.datasets[1].data[i] = response2.data.rows[i].USED;
        }

        this.renderChart(this.chartdata, this.options)
    }
};
