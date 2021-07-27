<template>

<transition name="modal" appear>
  <div class="my-modal d-flex align-items-center justify-content-center" 
    v-if="visible" @click.self="handleWrapperClick">
    <div class="my-modal__dialog ">
     
        <!-- update:visible', !visible 모달 바깥을 클릭해서 모달을 닫음 -->
        
        
      <div class="my-modal__body">
        <button  @click="$emit('update:visible', !visible)">
          <img class="my-modal__close" src="@/assets/delete.png" alt="닫기">
        </button>
        
        <slot></slot>
      </div>
    </div>
  </div>
</transition>

</template>

<script>

export default {
  name: 'myModal',
  props: {
    visible: {
      type: Boolean,
      require: true,
      default: false
    },
    title: {
      type: String,
      require: false,
    },
  },
  methods: {
    handleWrapperClick(){
      this.$emit('update:visible', false)
    },
    
  },
}
</script>

<style lang="scss" >

$module: 'my-modal';
.#{$module} {
  background-color: rgba(0, 0, 0, 0.527);
  top: 0; right: 0; bottom: 0; left: 0;
  position: fixed;
  display:flex; /* 내용을 중앙정렬 하기위해 flex 사용 */
	align-items:center; /* 위아래 기준 중앙정렬 */
	justify-content:center; /* 좌우 기준 중앙정렬 */
  z-index:1200;

  &__close{
  width:10%;
  float:right;
}
  &__dialog{    
    border-radius: 20px;
    background-color: rgba(38, 22, 90, 0.924);
    display: flex;
    border: 2px solid rgb(76, 78, 167);
    width: 600px;
  }
  &container{
    width: 600px;
  }
  &_header {
    padding: 25px;
  }
  &__body {
    padding: 25px;
  }
  
}
</style>