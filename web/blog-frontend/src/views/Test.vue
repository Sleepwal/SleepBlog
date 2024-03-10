<template>
		<div style="padding-top: 10%">
        <div class="bullet-wrap">
            <div class="bullet-item">
                1111111111111111111
            </div>
        </div>
		</div>

</template>

<style scoped>

.bullet-wrap {
  height: 375px;
  background-color: #eee;
  position: relative;
}
.bullet-item {
  position: absolute;
		animation: rightToLeft 18s linear;
}

@keyframes rightToLeft {
    0% {
        transform: translate(100vw);
    }
    100% {
        transform: translate(-100%);
    }
}
</style>

<script setup>
</script>