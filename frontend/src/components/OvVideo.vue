<template>
	<video autoplay/>
</template>

<script>
export default {
	name: 'OvVideo',

	props: {
		streamManager: Object,
		
	},

	mounted () {
		console.log('mounted 스트리머')
		console.log(this.streamManager)
		console.log(this.$el)
		this.streamManager.addVideoElement(this.$el);
	},
};
</script>
