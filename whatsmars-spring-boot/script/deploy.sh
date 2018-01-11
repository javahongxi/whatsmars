#!/usr/bin/env bash
# 此脚本在本地执行
# 默认发布到测试主机 t1234x.add.bjyz.toutiao.im
# 传入参数prod，则发布到正式环境的4台主机 "t2001x.add.bjyz.toutiao.im" "t2002x.add.bjyz.toutiao.im" "t2003x.add.bjhc.toutiao.im" "t2004x.add.bjhc.toutiao.im

app_profiles=${1:-"test"}
app_name=${2:-"whatsmars-spring-boot"}
remote_deploy_path="/data/toutiao"
remote_deploy_user=toutiao
host_file=host_${app_profiles}.txt

if [ ! -e $host_file ]; then
    echo "host file not exists, path:${host_file}"
    exit 1
fi

remote_deploy_hosts=(`cat ${host_file} | tr '\n' ' '`)
echo "deploy env => $app_profiles, to => ${remote_deploy_hosts[@]}:${remote_deploy_path}"

# 打包
project_path="$(cd `dirname $0`; pwd)/.."
cd ${project_path}
mvn clean package -Dmaven.test.skip=true

# 部署
now=`date +"%Y%m%d%H%M%S"`
jar_path="${project_path}/target/${app_name}.jar"
for host in ${remote_deploy_hosts[@]}; do
    echo "start deploy ${jar_path} to $host"
    ssh ${remote_deploy_user}@${host} "${remote_deploy_path}/stop.sh"
    ssh ${remote_deploy_user}@${host} "mkdir -p ${remote_deploy_path}/deploy_backup; mv ${remote_deploy_path}/${app_name}.jar ${remote_deploy_path}/deploy_backup/${app_name}-${now}.jar"
    scp ${jar_path} ${remote_deploy_user}@${host}:${remote_deploy_path}/
    ssh ${remote_deploy_user}@${host} "cd ${remote_deploy_path}/ ; ./start.sh ${app_profiles}"
    echo "deploy ${host} ok"
    sleep 10
done
echo "done."