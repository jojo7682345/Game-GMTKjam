#define move
///interact();
if(game.money>=1){
targ = instance_create(x,y,obj_target);
targ.stage = 0;
targ.goal = id;
stage = -1;
game.money--;
}

#define init_moveable
///init_interactable();
targ = noone;

#define destroy_movable
///destroy_moveable
instance_destroy(targ);
