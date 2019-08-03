#define battle
///battle
if(!instance_exists(target)){
    var t = target;
    target = target2;
    target2 = t;
}
if(game.timer<=0){
    switch(stage){
        case -1:
            if(!instance_exists(targ)){
            stage = 0;
                break;
            }
            navigate(targ);
        break;
        case 0:
            if(instance_exists(target)){
                    navigate(instance_nearest(x,y,target));
            }else{
                    navigate(game.castle);
            }
            
        break;
        default:
        stage--;
    }
    if(instance_exists(target)&&stage==0){
        for(var i = 0; i < instance_number(target); i++){
            var inst = instance_find(target,i);
            if(point_distance(x,y,inst.x,inst.y)<range){
                stage = 3;

                    inst.stage += 2;
                    inst.hp -= damage;
                    if(inst.hp <= 0){
                    if(inst.object_index==enemy.object_index){
                        game.money+=40;
                    }
                        instance_destroy(inst);
                    
                }
            }
        }
    }
}


#define init_battler
///init_battler(target,range,damage,seccondary);
event_inherited();
register_pathfinder();
stage = 0;
damage = argument2;
range = argument1;
target = argument0;
target2 = argument3;
