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
                        label: "Fixed Size",
                        data: [],
                        backgroundColor: "transparent",
                        borderColor: "rgba(187,238,17,1)",
                        pointBackgroundColor: "white"
                    }, {
                        label: "Variable Size",
                        data: [],
                        backgroundColor: "transparent",
                        borderColor: "rgba(60,213,255,1)",
                        pointBackgroundColor: "white"
                    }, {
                        label: "Database Buffers",
                        data: [],
                        backgroundColor: "transparent",
                        borderColor: "rgba(255,156,119,1)",
                        pointBackgroundColor: "white"
                    }, {
                        label: "Redo Buffers",
                        data: [],
                        backgroundColor: "transparent",
                        borderColor: "rgba(255,99,132,1)",
                        pointBackgroundColor: "white"
                    }
                ]
            },
            options: {
                scales: {
                    yAxes: [
                        {
                            ticks: {
                                beginAtZero: true,
                                callback: function (value) {
                                    if (value < 1e3) 
                                        return value;
                                    
                                    if (value >= 1e3 && value < 1e6) 
                                        return + (value / 1e3).toFixed(1) + "K";
                                    
                                    if (value >= 1e6 && value < 1e9) 
                                        return + (value / 1e6).toFixed(1) + "M";
                                    
                                    if (value >= 1e9 && value < 1e12) 
                                        return + (value / 1e9).toFixed(1) + "B";
                                    
                                    if (value >= 1e12) 
                                        return + (value / 1e12).toFixed(1) + "T";
                                    

                                }
                            }
                        }
                    ]
                },
                responsive: true,
                maintainAspectRatio: false,
                title: {
                    display: true,
                    text: "SGA Values",
                    fontSize: 20
                }
            }
        }
    ),
    async mounted() {
        let response2 = await axios.get("http://localhost:5001/getSystemGlobalArea/");

        for (var i = 0; i < response2.data.rows.length; i++) {
            var time = moment(response2.data.rows[i].TIMESTAMP).format('MMM DD, YYYY HH:mm:ss');
            this.chartdata.labels.indexOf(time) === -1 ? this.chartdata.labels.push(time) : console.log("");
            if (response2.data.rows[i].NAME == "Fixed Size") {
                this.chartdata.datasets[0].data.push(response2.data.rows[i].VALUE);
            }
            if (response2.data.rows[i].NAME == "Variable Size") {
                this.chartdata.datasets[1].data.push(response2.data.rows[i].VALUE);
            }
            if (response2.data.rows[i].NAME == "Database Buffers") {
                this.chartdata.datasets[2].data.push(response2.data.rows[i].VALUE);
            }
            if (response2.data.rows[i].NAME == "Redo Buffers") {
                this.chartdata.datasets[3].data.push(response2.data.rows[i].VALUE);
            }

        }

        this.renderChart(this.chartdata, this.options)
    }
};
