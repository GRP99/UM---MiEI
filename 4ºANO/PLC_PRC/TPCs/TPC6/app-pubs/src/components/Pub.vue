<template>
	<div id="maintenance">
		<h3 style="text-align:center"> <b>{{ idp.toUpperCase() }}</b></h3>
		<v-simple-table height="500px">
			<template v-slot:default>
				<thead>
					<tr>
						<th class="text-left"></th>
						<th class="text-left"></th>
					</tr>
				</thead>
				<tbody>
					<tr v-for="(pub, index) in info_pub" :key="index" >
						<template v-if="pub.address">
							<td> <b>Address</b> </td>
							<td> {{ pub.address }} </td>
						</template>
						<template v-if="pub.booktitle">
							<td> <b>Booktitle</b> </td>
							<td> {{ pub.booktitle }} </td>
						</template>
						<template v-if="pub.chapter">
							<td> <b>Chapter</b> </td>
							<td> {{ pub.chapter }} </td>
						</template>
						<template v-if="pub.doi">
							<td> <b>DOI</b> </td>
							<td> {{ pub.doi }} </td>
						</template>
						<template v-if="pub.howpublished">
							<td> <b>How Published?</b> </td>
							<td> {{ pub.howpublished }} </td>
						</template>
						<template v-if="pub.isbn">
							<td> <b>ISBN</b> </td>
							<td> {{ pub.isbn }} </td>
						</template>
						<template v-if="pub.issn">
							<td> <b>ISSN</b> </td>
							<td> {{ pub.issn }} </td>
						</template>
						<template v-if="pub.month">
							<td> <b>Month</b> </td>
							<td> {{ pub.month }} </td>
						</template>
						<template v-if="pub.nome">
							<td> <b>Nome</b> </td>
							<td> {{ pub.nome }} </td>
						</template>
						<template v-if="pub.number">
							<td> <b>Number</b> </td>
							<td> {{ pub.number }} </td>
						</template>
						<template v-if="pub.pages">
							<td> <b>Pages</b> </td>
							<td> {{ pub.pages }} </td>
						</template>
						<template v-if="pub.publisher">
							<td> <b>Publisher</b> </td>
							<td> {{ pub.publisher }} </td>
						</template>
						<template v-if="pub.school">
							<td> <b>School</b> </td>
							<td> {{ pub.school }} </td>
						</template>
						<template v-if="pub.title">
							<td> <b>Title</b> </td>
							<td> {{ pub.title }} </td>
						</template>
						<template v-if="pub.type">
							<td> <b>Type</b> </td>
							<td> {{ pub.type }} </td>
						</template>
						<template v-if="pub.uri">
							<td> <b>URI</b> </td>
							<td> {{ pub.uri }} </td>
						</template>
						<template v-if="pub.url">
							<td> <b>URL</b> </td>
							<td> {{ pub.url }} </td>
						</template>
						<template v-if="pub.volume">
							<td> <b>Volume</b> </td>
							<td> {{ pub.volume }} </td>
						</template>
						<template v-if="pub.year">
							<td> <b>Year</b> </td>
							<td> {{ pub.year }} </td>
						</template>
						<template v-if="pub.wasWritten">
							<td> <b>Was Written by</b> </td>
							<td>
								<template>
									<v-simple-table>
										<template v-slot:default>
										<thead>
										</thead>
										<tbody>
											<tr v-for="(author,i) in pub.wasWritten" :key="i">
												<td> <i> {{ author }} </i></td>
											</tr>
										</tbody>
										</template>
									</v-simple-table>
								</template>
							</td>
						</template>
						<template v-if="pub.wasEdit">
							<td> <b>Was Edit by</b> </td>
							<td>
								<template>
									<v-simple-table>
										<template v-slot:default>
										<thead>
										</thead>
										<tbody>
											<tr v-for="(editor,i) in pub.wasEdit" :key="i">
												<td> <i> {{ editor }} </i> </td>
											</tr>
										</tbody>
										</template>
									</v-simple-table>
								</template>
							</td>
						</template>
					</tr>
				</tbody>
			</template>
		</v-simple-table>
	</div>
</template>

<script>
import axios from "axios";

export default {
	name: "Pub",
	props: ["idp"],

  	data() {
    	return {
      		info_pub: []
    	};
  	},

  	created: function () {
    	axios.get("http://localhost:8080/api-server/pubs/" + this.idp)
      		.then(res => {
        		this.info_pub = res.data;
      		})
      		.catch(e => {
				console.log("ERROR in get pub:" + e)
			});
	}
};
</script>

<style>
#maintenance {
  background-color: rgb(255, 255, 255);
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