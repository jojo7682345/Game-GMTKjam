#define move
///interact();
if(game.action==false&&stage==0){
targ = instance_create(x,y,obj_target);
targ.stage = 0;
targ.goal = id;
stage = -1;
game.action=true;
}

#define init_moveable
///init_interactable();
targ = noone;

#define destroy_movable
///destroy_moveable
instance_destroy(targ);
