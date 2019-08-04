#define battle
///battle
if(!instance_exists(target)){
    var t = target;
    target = target2;
    target2 = t;
}
if(game.timer<=0){
    switch(stage){
        case -7:
        audio_play_sound(bomb_fuse,0,0);
        stage++;
            break;
        case -6:
        stage++;
        audio_play_sound(bomb_fuse,0,0);
            break;
        case -5:
        stage++;
        audio_play_sound(bomb_fuse,0,0);
            break;
        case -4:
            stage++;
            audio_play_sound(bomb_fuse,0,0);
            break;
        case -2:
            var dir = point_direction(0,0,toX-x,toY-y);
            x += lengthdir_x(min(40,sqrt(power(toX-x,2)+power(toY-y,2))),dir);
             y += lengthdir_y(min(40,sqrt(power(toX-x,2)+power(toY-y,2))),dir);
             var xx = point_distance(catapult.x,catapult.y,x,y)/point_distance(catapult.x,catapult.y,toX,toY)
             var maxheight = 64;
             height = -(maxheight/0.25)*(xx-0.5)*(xx-0.5) + maxheight;
             if(round((toX/x)*20)==20&&round((toY/y)*20)==20){
                stage = 0;
                height = 0;
                camera.shake = 4;
                audio_play_sound(snd_knight_crash,0,0);
             }
        break;
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
                    audio_play_sound(snd_attack,0,0);
                    inst.stage += 2;
                    inst.hp -= damage;
                    if(inst.hp <= 0){
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
